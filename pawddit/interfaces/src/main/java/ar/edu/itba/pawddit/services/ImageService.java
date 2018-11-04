package ar.edu.itba.pawddit.services;

import java.util.Optional;

import ar.edu.itba.pawddit.model.Image;

public interface ImageService {

	public Optional<Image> findByToken(String token);
	public String saveImage(byte[] image);
	
}
