package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import ar.edu.itba.pawddit.model.Image;

public interface ImageDao {

	public Optional<Image> findByToken(String token);
	public String saveImage(byte[] image, String token);
	
}
