package ar.edu.itba.pawddit.webapp.form.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.itba.pawddit.webapp.form.validators.UsernameNotRepeatedValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameNotRepeatedValidator.class)
@Documented
public @interface UsernameNotRepeated {

	public String message() default "{ar.edu.itba.pawddit.webapp.form.formAnnotations.UsernameNotRepeated.Message}";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}