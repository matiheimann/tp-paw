package ar.edu.itba.pawddit.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.persistence.ImageDao;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageDao imageDao;

	@Override
	public Optional<byte[]> findByToken(final String token) {
		return imageDao.findByToken(token);
	}

	@Override
	public String saveImage(final byte[] image) {
		final String token = UUID.randomUUID().toString();
		return imageDao.saveImage(image, token);
	}

}
