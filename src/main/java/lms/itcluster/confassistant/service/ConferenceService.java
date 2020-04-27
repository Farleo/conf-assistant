package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.ScheduleConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;

import java.io.IOException;
import java.util.List;

public interface ConferenceService {

    Conference findById(Long id);

    List<ConferenceDTO> getAllConferencesDTO();

    ListConferenceDTO getListConferencesDTO();

    ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser);

    ListConferenceDTO getAllConferenceDTOForCurrentSpeaker(CurrentUser currentUser);

    ConferenceDTO getConferenceDTOById(Long id);
    
    ConferenceDTO getConferenceDTObyAlias(String alias);

    ListConferenceDTO getConferencesDTOByOwnerId(Long id);

    void addNewConference(ConferenceDTO conferenceDTO, byte[] photo, String originalPhotoName) throws IOException;

    void updateConference(ConferenceDTO conferenceDTO, byte[] photo, String originalPhotoName) throws IOException;

    void deleteConference(Long confId);

    ScheduleConferenceDTO getConferenceForSchedule(Long confId);

    ConferenceDTO getConferenceDTOByTopicId(Long topicId);

    boolean registerCurrentUserForConference(Long confId, Long userId);
}
