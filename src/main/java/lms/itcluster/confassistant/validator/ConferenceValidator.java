package lms.itcluster.confassistant.validator;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class ConferenceValidator implements Validator {
	
	@Autowired
	ConferenceRepository conferenceRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return ConferenceDTO.class.equals(aClass);
	}

	@Override
	public void validate(Object object, Errors errors){
		ConferenceDTO conferenceDTO = (ConferenceDTO) object;
		Conference byName = conferenceRepository.findByName(conferenceDTO.getName());
		Conference byAlias = conferenceRepository.findByAlias(conferenceDTO.getAlias());
		LocalDate localDate = LocalDate.now();
		if(conferenceDTO.getBeginDate()==null||conferenceDTO.getFinishDate()==null||conferenceDTO.getBeginDate().after(conferenceDTO.getFinishDate())){
			errors.rejectValue("finishDate", "beginDate.after.finishDate", "Invalid date");
		}
		if (conferenceDTO.getBeginDate().before(java.sql.Date.valueOf(localDate))){
			errors.rejectValue("finishDate", "beginDate.after.localDate", "invalid date to current date");
		}
		if(byName!=null && conferenceDTO.getConferenceId() != byName.getConferenceId()){
			errors.rejectValue("name", "name.unique", "This conference name already exist");
		}
		if(byAlias!=null && conferenceDTO.getConferenceId() != byAlias.getConferenceId()){
			errors.rejectValue("alias", "alias.unique", "This alias name already exist");
		}
	}
	
}
