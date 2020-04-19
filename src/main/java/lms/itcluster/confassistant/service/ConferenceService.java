package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.ScheduleConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<ScheduleConferenceDTO> getConferencesForSchedule(Pageable pageable);

    Long getConfIdByTopicId(Long topicId);

    boolean isCurrentUserPresentAtTopicConference(Long userId, Long topicId);

    boolean registerCurrentUserForConference(Long confId, Long userId);
}
