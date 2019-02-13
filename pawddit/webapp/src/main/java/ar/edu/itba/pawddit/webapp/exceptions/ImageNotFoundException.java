package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class ImageNotFoundException extends NotFoundException {

	public ImageNotFoundException() {
		super("ImageNotFound");
	}
	
}
