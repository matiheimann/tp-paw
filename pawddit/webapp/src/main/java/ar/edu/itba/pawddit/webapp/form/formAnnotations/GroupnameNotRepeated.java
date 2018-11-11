package ar.edu.itba.pawddit.webapp.form.formAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.itba.pawddit.webapp.form.formValidators.GroupnameNotRepeatedValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupnameNotRepeatedValidator.class)
@Documented
public @interface GroupnameNotRepeated {

	public String message() default "{ar.edu.itba.pawddit.webapp.form.formAnnotations.GroupnameNotRepeated.Message}";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}
