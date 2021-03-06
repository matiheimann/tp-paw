package ar.edu.itba.pawddit.webapp.form.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;
import ar.edu.itba.pawddit.webapp.form.annotations.GroupnameNotRepeated;

public class GroupnameNotRepeatedValidator implements ConstraintValidator<GroupnameNotRepeated, CreateGroupForm> {
	
	@Autowired
	private GroupService gs;

	@Override
	public void initialize(GroupnameNotRepeated constraintAnnotation) {
	}

	@Override
	public boolean isValid(CreateGroupForm form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("name").addConstraintViolation();
		return !gs.findByName(null, form.getName()).isPresent();
	}

}