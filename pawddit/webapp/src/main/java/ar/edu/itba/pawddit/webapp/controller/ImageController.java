package ar.edu.itba.pawddit.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.edu.itba.pawddit.services.ImageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService is;
	
	@RequestMapping(value = "/image/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImageAsByteArray(@PathVariable final String token) {
	   Optional<byte[]> img = is.findByToken(token);
	   if (!img.isPresent())
		   return null;
	   
	   return img.get();
	}
}
