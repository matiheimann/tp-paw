package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class PostNotFoundException extends NotFoundException {

	public PostNotFoundException() {
		super("PostNotFound");
	}

}
