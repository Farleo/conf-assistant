package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;

import java.util.List;

public interface ConferenceService {

    List<Conference> getAllConferences();

    Conference findById(long id);

    List<ConferenceDTO> getAllConferencesDTO();

    ListConferenceDTO getListConferencesDTO();

    ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser);

    ConferenceDTO getConferenceDTOById(Long id);

    ListConferenceDTO getConferencesDTOByOwnerId(Long id);

}
