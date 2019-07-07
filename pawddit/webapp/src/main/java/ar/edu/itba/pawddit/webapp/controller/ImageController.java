package ar.edu.itba.pawddit.webapp.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.services.ImageService;
import ar.edu.itba.pawddit.webapp.exceptions.ImageNotFoundException;

@Path("images")
@Component
public class ImageController {
	
	@Autowired
	private ImageService is;
	
	@GET
	@Path("/{token}")
	@Produces(value = { MediaType.IMAGE_JPEG_VALUE, })
	public Response getImage(
			@PathParam("token") final String token) {
		
		byte[] img = is.findByToken(token).orElseThrow(ImageNotFoundException::new);
		final CacheControl cache = new CacheControl();
		cache.setNoTransform(false);
		cache.setMaxAge(31536000);
		
		return Response.ok(img).cacheControl(cache).header("Pragma", null).build();
	}
}
