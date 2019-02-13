package ar.edu.itba.pawddit.services.exceptions;

@SuppressWarnings("serial")
public class NoPermissionsException extends RuntimeException {
	
	public NoPermissionsException() {
		super("NoPermissions");
	}

}
