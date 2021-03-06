package ar.edu.itba.pawddit.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.webapp.dto.PostDto;
import ar.edu.itba.pawddit.webapp.dto.PostsDto;

@Path("posts")
@Component
public class IndexController {
	
	private static final int POSTS_PER_PAGE = 5;

	@Autowired
	private PostService ps;

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getAllPosts(
			@QueryParam("page") @DefaultValue("1") Integer page, 
			@QueryParam("sort") @DefaultValue("new") String sort, 
			@QueryParam("time") @DefaultValue("all") String time) {

		final List<Post> posts = ps.findAll(POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort, time);
		final int count = (ps.findAllCount(time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE;
		return Response.ok(
			PostsDto.fromPosts(
				posts.stream()
				.map(PostDto::fromPost)
				.collect(Collectors.toList()), 
				count
			)
		).build();
	}

}
