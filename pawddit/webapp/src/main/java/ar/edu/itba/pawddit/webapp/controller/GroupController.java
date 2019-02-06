package ar.edu.itba.pawddit.webapp.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.SubscriptionService;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;
import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;
import ar.edu.itba.pawddit.webapp.dto.GroupDto;
import ar.edu.itba.pawddit.webapp.dto.PageCountDto;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;

@Path("/api/groups")
@Component
public class GroupController {

	private static final int GROUPS_PER_PAGE = 7;

	@Autowired
	private GroupService gs;

	@Autowired
	private SubscriptionService ss;
	
	@Autowired
	private PawdditUserDetailsService userDetailsService;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getGroups(
			@QueryParam("page") @DefaultValue("1") int page, 
			@QueryParam("search") @DefaultValue("") String search) {
		
		if (page == 0) {
			final int count = (gs.searchGroupsByStringCount(search)+GROUPS_PER_PAGE-1)/GROUPS_PER_PAGE;
			return Response.ok(PageCountDto.fromPageCount(count)).build();
		}
		else {
			final List<Group> groups = gs.searchGroupsByString(search, GROUPS_PER_PAGE, (page-1)*GROUPS_PER_PAGE);
			return Response.ok(
				new GenericEntity<List<GroupDto>>(
					groups.stream()
						.map(GroupDto::fromGroup)
						.collect(Collectors.toList())
				) {}
			).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response createGroup(
			@Valid @FormDataParam("createGroup") final CreateGroupForm form) {

		final User user = userDetailsService.getLoggedUser();
		if (user != null) {
			final Group group = gs.create(form.getName(), LocalDateTime.now(), form.getDescription(), user);
			final URI uri = uriInfo.getAbsolutePathBuilder().path(group.getName()).build();
			return Response.created(uri).build();
			
		}
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{groupName}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response getGroup(
			@PathParam("groupName") final String groupName) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			return Response.ok(GroupDto.fromGroup(group)).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{groupName}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response deleteGroup(
			@PathParam("groupName") final String groupName) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group group = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			if (user != null) {
				gs.delete(user, group);
				return Response.noContent().build();
			}
			else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (NoPermissionsException e) {
			return Response.status(Status.FORBIDDEN).build();
		}
	}
	
	@PUT
	@Path("/{groupName}/subscribe")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response groupSubscribe(
			@PathParam("groupName") final String groupName) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group g = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			if (user != null) {
				ss.suscribe(user, g);
				return Response.noContent().build();
			}
			else
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Path("/{groupName}/unsubscribe")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response groupUnsubscribe(
			@PathParam("groupName") final String groupName) {
		
		try {
			final User user = userDetailsService.getLoggedUser();
			final Group g = gs.findByName(user, groupName).orElseThrow(GroupNotFoundException::new);
			if (user != null) {
				ss.unsuscribe(user, g);
				return Response.noContent().build();
			}
			else
				return Response.status(Status.BAD_REQUEST).build();
		}
		catch (GroupNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}
