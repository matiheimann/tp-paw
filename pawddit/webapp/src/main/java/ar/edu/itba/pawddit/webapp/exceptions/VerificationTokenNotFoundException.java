package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class VerificationTokenNotFoundException extends NotFoundException {

	public VerificationTokenNotFoundException() {
		super("VerificationTokenNotFound");
	}

}
