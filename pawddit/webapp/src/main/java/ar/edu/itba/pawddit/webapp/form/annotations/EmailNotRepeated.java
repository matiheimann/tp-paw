package ar.edu.itba.pawddit.webapp.form.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.itba.pawddit.webapp.form.validators.EmailNotRepeatedValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotRepeatedValidator.class)
@Documented
public @interface EmailNotRepeated {

	public String message() default "{ar.edu.itba.pawddit.webapp.form.annotations.EmailNotRepeated.message}";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}