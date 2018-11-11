package ar.edu.itba.pawddit.webapp.form.formAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.itba.pawddit.webapp.form.formValidators.EmailNotRepeatedValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotRepeatedValidator.class)
@Documented
public @interface EmailNotRepeated {

	public String message() default "{ar.edu.itba.pawddit.webapp.form.formAnnotations.EmailNotRepeated.Message}";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}