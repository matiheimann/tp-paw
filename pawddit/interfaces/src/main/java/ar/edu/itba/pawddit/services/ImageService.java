package ar.edu.itba.pawddit.services;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {

	public Optional<byte[]> findByToken(String token);
	public String saveImage(byte[] image) throws IOException;
	
}
