package ar.edu.itba.pawddit.webapp.dto;

import javax.validation.ConstraintViolation;

public class FieldViolationDTO {
	private String field;
	private String violation;
	
	public FieldViolationDTO(final ConstraintViolation<?> constraintViolation) {
		this.setField(constraintViolation.getPropertyPath().toString());
		this.setViolation(constraintViolation.getMessage());
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getViolation() {
		return violation;
	}

	public void setViolation(String violation) {
		this.violation = violation;
	}
}