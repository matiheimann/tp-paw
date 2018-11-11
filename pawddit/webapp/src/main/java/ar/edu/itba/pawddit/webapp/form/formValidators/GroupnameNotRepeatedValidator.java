package ar.edu.itba.pawddit.webapp.form.formValidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;
import ar.edu.itba.pawddit.webapp.form.formAnnotations.GroupnameNotRepeated;

public class GroupnameNotRepeatedValidator implements ConstraintValidator<GroupnameNotRepeated, CreateGroupForm> {
	
	@Autowired
	private GroupService gs;

	@Override
	public void initialize(GroupnameNotRepeated constraintAnnotation) {
	}

	@Override
	public boolean isValid(CreateGroupForm form, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate( "{ar.edu.itba.pawddit.webapp.form.formAnnotations.GroupnameNotRepeated.Message}" ).addNode("name").addConstraintViolation();
		return !gs.findByName(form.getName()).isPresent();
	}

}