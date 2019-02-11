package ar.edu.itba.pawddit.webapp.exceptions;

public class RepeatedEmailException extends Exception{

	private static final long serialVersionUID = 1L;

	public RepeatedEmailException(String message) {
		super(message);
	}
	
}
