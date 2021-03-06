package ar.edu.itba.pawddit.webapp.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BeanParam;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

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
import ar.edu.itba.pawddit.webapp.dto.ExceptionDto;
import ar.edu.itba.pawddit.webapp.dto.GroupDto;
import ar.edu.itba.pawddit.webapp.dto.GroupsDto;
import ar.edu.itba.pawddit.webapp.dto.IsLoggedInDto;
import ar.edu.itba.pawddit.webapp.dto.PostDto;
import ar.edu.itba.pawddit.webapp.dto.PostsDto;
import ar.edu.itba.pawddit.webapp.dto.UserDto;
import ar.edu.itba.pawddit.webapp.exceptions.DTOValidationException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;
import ar.edu.itba.pawddit.webapp.form.ImageForm;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;
import ar.edu.itba.pawddit.webapp.form.validators.DTOConstraintValidator;

@Path("users")
@Component
public class UserController {
	
	private static final int GROUPS_PER_PAGE = 5;
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
	
	@Autowired
	private DTOConstraintValidator DTOValidator;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private TemplateEngine htmlTemplateEngine;
	
	@Autowired
	private String frontUrl;
	
	@Context
	private UriInfo uriInfo;
	
	private static final String VERIFICATION_TOKEN_TEMPLATE_NAME = "verificationToken.html";
	
	@POST
	@Path("/register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response createUser(
			@FormDataParam("createUser") final UserRegisterForm form) throws DTOValidationException {
		
		DTOValidator.validate(form, "Failed to validate User");
		
		final User user = us.create(form.getUsername(), form.getPassword(), form.getEmail(), false);
		final VerificationToken token = us.createToken(user);
		
		// Prepare the evaluation context
		final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context(LocaleContextHolder.getLocale());
		final String confirmationUrl = frontUrl + "confirm?token=" + token.getToken();
		ctx.setVariable("username", user.getUsername());
		ctx.setVariable("confirmationUrl", confirmationUrl);
		final String htmlContent = this.htmlTemplateEngine.process(VERIFICATION_TOKEN_TEMPLATE_NAME, ctx);
		final String subject = applicationContext.getMessage("mail.subject", null, LocaleContextHolder.getLocale());
		
		mss.sendVerificationToken("Pawddit.", user.getEmail(), subject, htmlContent);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
		return Response.created(uri).entity(UserDto.fromUser(user)).build();
	}
	
	@GET
	@Path("/confirm")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response confirmUser(
			@QueryParam("token") @DefaultValue("") String token) {

		final VerificationToken verificationToken = us.findToken(token).orElseThrow(VerificationTokenNotFoundException::new);
		final User user = verificationToken.getUser();
		us.enableUser(user);

		/* Auto Login */
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
		return Response.created(uri).entity(UserDto.fromUser(user)).build();
	}
	
	@GET
	@Path("/profile/{username}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUser(
			@PathParam("username") final String username) {
		
		final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
		return Response.ok(UserDto.fromUser(user)).build();
	}
	
	@GET
	@Path("/profile/{username}/lastPosts")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUserLastPosts(
			@PathParam("username") final String username) {
		
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
	
	@GET
	@Path("/profile/{username}/lastComments")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getUserLastComments(
			@PathParam("username") final String username) {
		
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
	
	@GET
	@Path("/me")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response isLoggedIn() {
		
		final User user = userDetailsService.getLoggedUser();
		if (user != null)
			return Response.ok(IsLoggedInDto.fromIsLoggedIn(true)).build();
		else 
			return Response.ok(IsLoggedInDto.fromIsLoggedIn(false)).build();
	}
	
	@GET
	@Path("/me/profile")
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
			@BeanParam final ImageForm imageForm) throws DTOValidationException {

		try {
			final User user = userDetailsService.getLoggedUser();
			if (user != null) {
				String imageId = null;
				if (imageForm != null && imageForm.getFileBodyPart() != null && imageForm.getFileBytes() != null) {
					DTOValidator.validate(imageForm, "Failed to validate Image");
					imageId = is.saveImage(imageForm.getFileBytes());
				}
				
				us.changeData(user, user.getUsername(), user.getPassword(), user.getEmail(), imageId);
				return Response.ok(UserDto.fromUser(user)).build();
			} 
			else 
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (IOException e) {
			return Response.status(Status.CONFLICT).entity(new ExceptionDto("InvalidImage")).build();
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
			final int count = (gs.findSubscribedByUserCount(user)+GROUPS_PER_PAGE-1)/GROUPS_PER_PAGE;
			return Response.ok(
				GroupsDto.fromGroups(
					groups.stream()
					.map(GroupDto::fromGroup)
					.collect(Collectors.toList()), 
					count
				)
			).build();
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
			final int count = (ps.findBySubscriptionsCount(user, time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE;
			return Response.ok(
				PostsDto.fromPosts(
					posts.stream()
					.map(PostDto::fromPost)
					.collect(Collectors.toList()), 
					count
				)
			).build();
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
