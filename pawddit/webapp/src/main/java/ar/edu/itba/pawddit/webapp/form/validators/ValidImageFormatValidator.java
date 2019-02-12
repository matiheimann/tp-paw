package ar.edu.itba.pawddit.webapp.form.validators;

import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ar.edu.itba.pawddit.webapp.form.ImageForm;
import ar.edu.itba.pawddit.webapp.form.annotations.ValidImageFormat;

public class ValidImageFormatValidator implements ConstraintValidator<ValidImageFormat, ImageForm> {
		
	@Override
	public void initialize(ValidImageFormat constraintAnnotation) {}

	@Override
	public boolean isValid(ImageForm form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("file").addConstraintViolation();
		
		return isValidFormat(form.getFileBytes());
	}
	
	private boolean isValidFormat(byte[] file) {
		
		if (file == null)
			return true;
		
		ImageInputStream image = null;
		try {
			image = ImageIO.createImageInputStream(file);
		} catch (IOException e1) {
			return false;
		}
		
		Iterator<ImageReader> readers = ImageIO.getImageReaders(image);
		
		if(!readers.hasNext())
			return false;
		
		return true;
	}

}
