package ar.edu.itba.pawddit.webapp.form.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;
import ar.edu.itba.pawddit.webapp.form.annotations.MatchingPasswords;

public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, UserRegisterForm> {

	@Override
	public void initialize(MatchingPasswords constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserRegisterForm form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate( "{ar.edu.itba.pawddit.webapp.form.formAnnotations.MatchingPasswords.Message}" ).addNode("repeatPassword").addConstraintViolation();
		return form.getPassword().equals(form.getRepeatPassword()) && !(form.getPassword().isEmpty() && form.getRepeatPassword().isEmpty());
	}

}
