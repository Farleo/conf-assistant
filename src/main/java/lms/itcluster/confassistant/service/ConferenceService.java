package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;

import java.util.List;

public interface ConferenceService {

    List<Conference> getAllConferences();

    Conference findById(long id);

    List<ConferenceDTO> getAllConferencesDTO();

    ListConferenceDTO getListConferencesDTO();

    ConferenceDTO getConferenceDTOById(Long id);

    ListConferenceDTO getConferencesDTOByOwnerId(Long id);
    
}
