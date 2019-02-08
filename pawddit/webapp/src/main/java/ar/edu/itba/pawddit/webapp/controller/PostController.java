package ar.edu.itba.pawddit.webapp.controller;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.ImageService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.PostVoteService;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;
import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;
import ar.edu.itba.pawddit.webapp.dto.PageCountDto;
import ar.edu.itba.pawddit.webapp.dto.PostDto;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreatePostForm;
import ar.edu.itba.pawddit.webapp.form.ImageForm;

@Path("/api/groups/{groupName}/posts")
@Component
public class PostController {
	
	private static final int POSTS_PER_PAGE = 5;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private PostVoteService pvs;
	
	@Autowired
	private ImageService is;
	
	@Autowired
	private PawdditUserDetailsService userDetailsService;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getGroupPosts(
			@PathParam("groupName") final String groupName,
			@QueryParam("page") @DefaultValue("1") Integer page, 
			@QueryParam("sort") @DefaultValue("new") String sort, 
			@QueryParam("time") @DefaultValue("all") String time) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group g = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			if (page == 0) {
				final int count = (ps.findByGroupCount(g, time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE;
				return Response.ok(PageCountDto.fromPageCount(count)).build();
			}
			else {
				final List<Post> posts = ps.findByGroup(g, POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort, time);
				return Response.ok(
					new GenericEntity<List<PostDto>>(
						posts.stream()
							.map(PostDto::fromPost)
							.collect(Collectors.toList())
					) {}
				).build();
			}
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response createPost(
			@Valid @FormDataParam("createPost") final CreatePostForm form,
			@BeanParam final ImageForm imageForm,
			@PathParam("groupName") final String groupName) {

		try {
			final User user = userDetailsService.getLoggedUser();
			final Group g = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			if (user != null) {
				String imageId = null;
				if (imageForm != null && imageForm.getFileBytes() != null)
					imageId = is.saveImage(imageForm.getFileBytes());

				final Post post = ps.create(form.getTitle(), form.getContent(), LocalDateTime.now(), g, user, imageId);
				final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(post.getPostid())).build();
				return Response.created(uri).entity(PostDto.fromPost(post)).build();
			}
			else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (IOException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{postId}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getPost(
			@PathParam("groupName") final String groupName, 
			@PathParam("postId") final long postId) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			final Post post = ps.findById(user, group, postId).orElseThrow(PostNotFoundException::new);
			return Response.ok(PostDto.fromPost(post)).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (PostNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/{postId}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response deletePost(
			@PathParam("groupName") final String groupName, 
			@PathParam("postId") final long postId) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			final Post post = ps.findById(user, group, postId).orElseThrow(PostNotFoundException::new);
			if (user != null) {
				ps.delete(user, group, post);
				return Response.noContent().build();
			}
			else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (PostNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (NoPermissionsException e) {
			return Response.status(Status.FORBIDDEN).build();
		}
	}
	
	@PUT
	@Path("/{postId}/upvote")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response postUpVote(
			@PathParam("groupName") final String groupName, 
			@PathParam("postId") final long postId) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			final Post post = ps.findById(user, group, postId).orElseThrow(PostNotFoundException::new);
			if (user != null) {
				pvs.upVote(user, post);
				return Response.noContent().build();
			}
			else
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (PostNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Path("/{postId}/downvote")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response postDownVote(
			@PathParam("groupName") final String groupName, 
			@PathParam("postId") final long postId) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			final Post post = ps.findById(user, group, postId).orElseThrow(PostNotFoundException::new);
			if (user != null) {
				pvs.downVote(user, post);
				return Response.noContent().build();
			}
			else
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (PostNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}
