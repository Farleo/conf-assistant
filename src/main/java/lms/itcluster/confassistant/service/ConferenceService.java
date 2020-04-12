package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ConferenceService {

    List<Conference> getAllConferences();

    Conference findById(long id);

    List<ConferenceDTO> getAllConferencesDTO();

    ListConferenceDTO getListConferencesDTO();

    ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser);

    ConferenceDTO getConferenceDTOById(Long id);

    ListConferenceDTO getConferencesDTOByOwnerId(Long id);

    void addNewConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException;

    void updateConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException;

    void deleteConference(Long confId);
}
