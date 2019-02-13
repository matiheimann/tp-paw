package ar.edu.itba.pawddit.webapp.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;


@SuppressWarnings("serial")
public class DTOValidationException extends RuntimeException {
	
	private final Set<? extends ConstraintViolation<?>> constraintViolations;
	
	public DTOValidationException(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(message);
		this.constraintViolations = constraintViolations;
	}

	public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}
	
}
