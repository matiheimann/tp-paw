package ar.edu.itba.pawddit.services;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

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
	public String saveImage(final byte[] image) throws IOException {
		final String token = UUID.randomUUID().toString();
		
		ByteArrayInputStream in = new ByteArrayInputStream(image);
		BufferedImage img = ImageIO.read(in);
		
		int width = img.getWidth();
		int height = img.getHeight();
		int maxW = 770;
		int maxH = 500;
		if (width > maxW && height < maxH) {
			height = (maxW * height) / width; 
			width = maxW;
		}
		else if (height > maxH && width < maxW) {
			width = (maxH * width) / height;
			height = maxH;
		}
		else if (width > maxW && height > maxH) {
			if (width-maxW > height-maxH) {
				height = (maxW * height) / width; 
				width = maxW;
			}
			else {
				width = (maxH * width) / height;
				height = maxH;
			}
		}
		
		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(imageBuff, "png", buffer);
        
		return imageDao.saveImage(buffer.toByteArray(), token);
	}

}
