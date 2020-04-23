package lms.itcluster.confassistant.annotation;

import lms.itcluster.confassistant.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { UniqueEmailValidator.class })
@NotNull
@NotBlank
@Email
public @interface UniqueEmail {

    String message() default "User with this email address already exist";


    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };
}

