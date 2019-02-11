package ar.edu.itba.pawddit.webapp.exceptions;

public class RepeatedUsernameException extends Exception{

	private static final long serialVersionUID = 1L;

	public RepeatedUsernameException(String message) {
		super(message);
	}

}
