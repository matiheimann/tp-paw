package ar.edu.itba.pawddit.webapp.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.ImageService;
import ar.edu.itba.pawddit.services.MailSenderService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;
import ar.edu.itba.pawddit.webapp.dto.CommentDto;
import ar.edu.itba.pawddit.webapp.dto.GroupDto;
import ar.edu.itba.pawddit.webapp.dto.PageCountDto;
import ar.edu.itba.pawddit.webapp.dto.PostDto;
import ar.edu.itba.pawddit.webapp.dto.UserDto;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;
import ar.edu.itba.pawddit.webapp.form.ChangeProfilePictureForm;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Path("users")
@Component
public class UserController {
	
	private static final int GROUPS_PER_PAGE = 7;
	private static final int POSTS_PER_PAGE = 5;

	@Autowired
	private UserService us;

	@Autowired
	private GroupService gs;

	@Autowired
	private PostService ps;
	
	@Autowired
	private CommentService cs;

	@Autowired
	private MailSenderService mss;
	
	@Autowired
	private ImageService is;
	
	@Autowired
	private PawdditUserDetailsService userDetailsService;
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	@Path("/register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response createUser(
			@Valid @FormDataParam("createUser") final UserRegisterForm form) {

		final User user = us.create(form.getUsername(), form.getPassword(), form.getEmail(), false);
		final VerificationToken token = us.createToken(user);
		mss.sendVerificationToken(user, token, uriInfo.getBaseUri().toString(), LocaleContextHolder.getLocale());
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/confirm")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response confirmUser(
			@QueryParam("token") @DefaultValue("") String token) {

		try {
			 final VerificationToken verificationToken = us.findToken(token).orElseThrow(VerificationTokenNotFoundException::new);
			 final User user = verificationToken.getUser();
			 us.enableUser(user);
			 
			 /* Auto Login */
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			final URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
			return Response.created(uri).build();
		}
		catch (VerificationTokenNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/profile/{username}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUser(
			@PathParam("username") final String username) {
		
		try {
			final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
			return Response.ok(UserDto.fromUser(user)).build();
		}
		catch (UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/profile/{username}/lastPosts")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUserLastPosts(
			@PathParam("username") final String username) {
		
		try {
			final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
			final List<Post> posts = ps.findByUser(user, 5, 0, null, null);
			return Response.ok(
				new GenericEntity<List<PostDto>>(
					posts.stream()
						.map(PostDto::fromPost)
						.collect(Collectors.toList())
				) {}
			).build();
		}
		catch (UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/profile/{username}/lastComments")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUserLastComments(
			@PathParam("username") final String username) {
		
		try {
			final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
			final List<Comment> comments = cs.findByUser(user, 5, 0);
			return Response.ok(
				new GenericEntity<List<CommentDto>>(
					comments.stream()
						.map(CommentDto::fromComment)
						.collect(Collectors.toList())
				) {}
			).build();
		}
		catch (UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/me")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getMyUser() {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null)
			return Response.ok(UserDto.fromUser(user)).build();
		else 
			return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/me")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response modifyMyUser(
			@Valid @FormDataParam("modifyUser") final ChangeProfilePictureForm form) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			if (user != null) {
				String imageId = null;
				if (!form.getFile().isEmpty())
					imageId = is.saveImage(form.getFile().getBytes());
				
				us.changeData(user, user.getUsername(), user.getPassword(), user.getEmail(), imageId);
				return Response.ok(UserDto.fromUser(user)).build();
			} 
			else 
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (IOException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/me/subscribedGroups")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getMyGroups(
			@QueryParam("page") @DefaultValue("1") int page) {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final List<Group> groups = gs.findSubscribedByUser(user, GROUPS_PER_PAGE, (page-1)*GROUPS_PER_PAGE);
			return Response.ok(
				new GenericEntity<List<GroupDto>>(
					groups.stream()
						.map(GroupDto::fromGroup)
						.collect(Collectors.toList())
				) {}
			).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/me/subscribedGroups/pageCount")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getMyGroupsPageCount() {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final int count = (gs.findSubscribedByUserCount(user)+GROUPS_PER_PAGE-1)/GROUPS_PER_PAGE;
			return Response.ok(PageCountDto.fromPageCount(count)).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/me/feedPosts")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getFeedPosts(
			@QueryParam("page") @DefaultValue("1") Integer page, 
			@QueryParam("sort") @DefaultValue("new") String sort, 
			@QueryParam("time") @DefaultValue("all") String time) {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final List<Post> posts = ps.findBySubscriptions(user, POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort, time);
			return Response.ok(
				new GenericEntity<List<PostDto>>(
					posts.stream()
						.map(PostDto::fromPost)
						.collect(Collectors.toList())
				) {}
			).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/me/feedPosts/pageCount")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getFeedPostsPageCount(
			@QueryParam("time") @DefaultValue("all") String time) {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final int count = (ps.findBySubscriptionsCount(user, time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE;
			return Response.ok(PageCountDto.fromPageCount(count)).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("me/recommendedGroups")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getRecommendedGroups() {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final List<Group> groups = gs.findRecommendedByUser(user, GROUPS_PER_PAGE);
			return Response.ok(
				new GenericEntity<List<GroupDto>>(
					groups.stream()
						.map(GroupDto::fromGroup)
						.collect(Collectors.toList())
				) {}
			).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
}
