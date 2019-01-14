package ar.edu.itba.pawddit.webapp.form.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;
import ar.edu.itba.pawddit.webapp.form.annotations.UsernameNotRepeated;

public class UsernameNotRepeatedValidator implements ConstraintValidator<UsernameNotRepeated, UserRegisterForm> {
	
	@Autowired
	private UserService us;

	@Override
	public void initialize(UsernameNotRepeated constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserRegisterForm form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate( "{ar.edu.itba.pawddit.webapp.form.formAnnotations.UsernameNotRepeated.Message}" ).addNode("username").addConstraintViolation();
		return !us.findByUsername(form.getUsername()).isPresent();
	}

}