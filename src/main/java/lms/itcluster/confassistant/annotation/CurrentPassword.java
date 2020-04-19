package lms.itcluster.confassistant.annotation;

import lms.itcluster.confassistant.validator.CurrentPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CurrentPasswordValidator.class)
@Documented
public @interface CurrentPassword {

    String message() default "The new password must be different from the current one.";
    String password();
    String userId();

    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };
}
