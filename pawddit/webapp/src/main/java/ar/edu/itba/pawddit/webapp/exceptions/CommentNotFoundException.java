package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class CommentNotFoundException extends NotFoundException {
	
	public CommentNotFoundException() {
		super("CommentNotFound");
	}

}
