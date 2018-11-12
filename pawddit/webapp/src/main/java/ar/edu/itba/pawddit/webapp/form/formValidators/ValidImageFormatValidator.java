package ar.edu.itba.pawddit.webapp.form.formValidators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.pawddit.webapp.form.FormWithImage;
import ar.edu.itba.pawddit.webapp.form.formAnnotations.ValidImageFormat;

public class ValidImageFormatValidator implements ConstraintValidator<ValidImageFormat, FormWithImage> {
		
	private final List<String> VALID_CONTENT_TYPES = Arrays.asList("image/png", "image/jpeg");

	@Override
	public void initialize(ValidImageFormat constraintAnnotation) {		
	}

	@Override
	public boolean isValid(FormWithImage form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate( "{ar.edu.itba.pawddit.webapp.form.formAnnotations.ValidImageFormat.Message}" ).addNode("file").addConstraintViolation();
		
		return isValidFormat(form.getFile());
	}
	
	private boolean isValidFormat(MultipartFile file) {
		
		if(file==null)
			return true;
		
		if (file.isEmpty())
			return true;
		
		if(!isValidContentType(file))
			return false;
		
		ImageInputStream image = null;
		try {
			image = ImageIO.createImageInputStream(file.getInputStream());
		} catch (IOException e1) {
			return false;
		}
		
		Iterator<ImageReader> readers = ImageIO.getImageReaders(image);
		
		if(!readers.hasNext())
			return false;
		
		return true;
	}
	
	private boolean isValidContentType(MultipartFile file) {
		return VALID_CONTENT_TYPES.contains(file.getContentType());
	}
}
