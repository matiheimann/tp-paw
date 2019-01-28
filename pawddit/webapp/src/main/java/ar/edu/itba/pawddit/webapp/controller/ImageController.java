package ar.edu.itba.pawddit.webapp.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.services.ImageService;
import ar.edu.itba.pawddit.webapp.exceptions.ImageNotFoundException;

@Path("/api/images")
@Component
public class ImageController {
	
	@Autowired
	private ImageService is;
	
	@GET
	@Path("/{token}")
	@Produces(value = { MediaType.IMAGE_JPEG_VALUE, })
	public Response getImage(
			@PathParam("token") final String token) {
		
		try {
			byte[] img = is.findByToken(token).orElseThrow(ImageNotFoundException::new);
			return Response.ok(img).build();
		}
		catch (ImageNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
