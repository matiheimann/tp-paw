package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

public interface ImageDao {

	public Optional<byte[]> findByToken(String token);
	public String saveImage(byte[] image, String token);
	
}
