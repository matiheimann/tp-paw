package ar.edu.itba.pawddit.webapp.form.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.itba.pawddit.webapp.form.validators.ValidImageFormatValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageFormatValidator.class)
@Documented
public @interface ValidImageFormat {

	public String message() default "{ar.edu.itba.pawddit.webapp.form.formAnnotations.ValidImageFormat.Message}";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}
