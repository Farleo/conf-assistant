package lms.itcluster.confassistant.validator;

import lms.itcluster.confassistant.annotation.CorrectDateAndTime;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.repository.TopicRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CorrectDateAndTimeValidator implements ConstraintValidator<CorrectDateAndTime, Object> {

    @Autowired
    private TopicRepository topicRepository;

    private String beginDateTime;
    private String finishTime;
    private String wrongRangeMessage;
    private String dateAreBusyMessage;
    private String topicId;

    @Override
    public void initialize(final CorrectDateAndTime constraintAnnotation) {
        wrongRangeMessage = constraintAnnotation.wrongRangeMessage();
        dateAreBusyMessage = constraintAnnotation.dateAreBusyMessage();
        topicId = constraintAnnotation.topicId();
        beginDateTime = constraintAnnotation.beginDateTime();
        finishTime = constraintAnnotation.finishTime();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            final LocalDateTime currentBeginDateTime = LocalDateTime.parse(BeanUtils.getProperty(value, beginDateTime), dateTimeFormatter);
            final LocalTime currentFinishTime = LocalTime.parse(BeanUtils.getProperty(value, finishTime));
            final LocalDateTime currentEndDateTime = LocalDateTime.of(currentBeginDateTime.toLocalDate(), currentFinishTime);

            LocalTime currentBeginTime = currentBeginDateTime.toLocalTime();

            /*Is begin time goes after finish time*/
            if (!currentBeginTime.isBefore(currentFinishTime)) {
                context.buildConstraintViolationWithTemplate(wrongRangeMessage)
                        .addPropertyNode(finishTime)
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }

            final long currentTopicId = Long.parseLong(BeanUtils.getProperty(value, topicId));

            Stream stream = topicRepository.findById(currentTopicId).get().getStream();

            /*Is range of date and time is free*/
            for (Topic topic : stream.getTopicList()) {
                if (topic.getTopicId() == currentTopicId) {
                    continue;
                }
                LocalDateTime topicStartDateTime = LocalDateTime.of(topic.getDate(), topic.getBeginTime());
                LocalDateTime topicEndDateTime = LocalDateTime.of(topic.getDate(), topic.getFinishTime());

                if (!((currentEndDateTime.isBefore(topicStartDateTime) && (currentBeginDateTime.isBefore(topicStartDateTime)))
                        || (currentBeginDateTime.isAfter(topicEndDateTime) && (currentEndDateTime.isAfter(topicEndDateTime))))) {
                    context.buildConstraintViolationWithTemplate(dateAreBusyMessage)
                            .addPropertyNode(beginDateTime)
                            .addConstraintViolation()
                            .disableDefaultConstraintViolation();
                    return false;
                }
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}
