package ar.edu.itba.pawddit.webapp.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class RepeatedUsernameException extends DTOValidationException{

	private static final long serialVersionUID = 1L;

	public RepeatedUsernameException(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations){
		super(message,constraintViolations);
	}

}
