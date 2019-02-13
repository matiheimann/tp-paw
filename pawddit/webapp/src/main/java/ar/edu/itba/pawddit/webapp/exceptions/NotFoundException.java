package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	
	public NotFoundException(final String message) {
		super(message);
	}
	
}