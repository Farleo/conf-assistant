package lms.itcluster.confassistant.annotation;

import lms.itcluster.confassistant.validator.ConfirmPasswordValidator;
import lms.itcluster.confassistant.validator.FieldMatchValidator;
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

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Documented
public @interface ConfirmPassword {

    String message() default "You enter wrong password";
    String password();
    String userId();

    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };
}
