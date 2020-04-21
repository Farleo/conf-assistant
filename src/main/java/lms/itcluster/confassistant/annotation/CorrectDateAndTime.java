package lms.itcluster.confassistant.annotation;

import lms.itcluster.confassistant.validator.CorrectDateAndTimeValidator;

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
@Constraint(validatedBy = CorrectDateAndTimeValidator.class)
@Documented
public @interface CorrectDateAndTime
{
    String message() default "Wrong date";
    String wrongRangeMessage() default "Wrong range of time";
    String dateAreBusyMessage() default "The current date and time are busy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String beginDateTime();
    String finishTime();
    String topicId();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        CorrectDateAndTime[] value();
    }
}
