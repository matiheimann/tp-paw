package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException() {
		super("UserNotFound");
	}

}
