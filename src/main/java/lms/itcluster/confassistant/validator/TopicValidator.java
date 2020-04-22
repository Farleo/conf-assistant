package lms.itcluster.confassistant.validator;

import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Service
public class TopicValidator implements Validator {

	@Autowired
	TopicRepository topicRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return SimpleTopicDTO.class.equals(aClass);
	}
	
	@Override
	public void validate(Object object, Errors errors){
		SimpleTopicDTO topic = (SimpleTopicDTO) object;
		Topic byName = topicRepository.findByName(topic.getName());
		LocalDate localDate = LocalDate.now();
		if(topic.getDate()==null){
			errors.rejectValue("date", "beginDate.after.finishDate", "Invalid date");
		}
		if(topic.getDate()==null||topic.getDate().isBefore(localDate)){
			errors.rejectValue("date", "beginDate.after.finishDate", "Date is before current date");
		}
		if(topic.getBeginTime()==null){
			errors.rejectValue("beginTime", "beginTime.null", "Begin time cannot be empty");
		}
		if(topic.getFinishTime()==null){
			errors.rejectValue("finishTime", "finishTime.null", "Finish time cannot be empty");
		}
		if(topic.getBeginTime().isAfter(topic.getFinishTime())){
			errors.rejectValue("beginTime", "beginTime.null", "Begin time cannot be after finish time");
		}
		if(byName!=null && topic.getTopicId() != byName.getTopicId()){
			errors.rejectValue("name", "name.unique", "This topic name already exist");
		}
		if(topic.getName().isEmpty()){
			errors.rejectValue("name", "name.unique", "Topic name is empty");
		}
	}
	
}
