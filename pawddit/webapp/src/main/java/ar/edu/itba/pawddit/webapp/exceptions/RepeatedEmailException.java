package ar.edu.itba.pawddit.webapp.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class RepeatedEmailException extends DTOValidationException{

	private static final long serialVersionUID = 1L;

	public RepeatedEmailException(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(message, constraintViolations);
	}
	
}
