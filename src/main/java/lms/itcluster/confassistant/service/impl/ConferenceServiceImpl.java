package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    @Qualifier("simpleConferenceMapper")
    private Mapper<Conference, ConferenceDTO> simpleMapper;

    @Autowired
    @Qualifier("conferenceMapper")
    private Mapper<Conference, ConferenceDTO> mapper;

    @Override
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(long id) {
        return conferenceRepository.findById(id).get();
    }

    @Override
    public ListConferenceDTO getListConferencesDTO() {
        return new ListConferenceDTO(getAllConferencesDTO());
    }

    @Override
    public ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser) {
        List<ConferenceDTO> list = new ArrayList<>();
        User user = userRepository.findById(currentUser.getId()).get();
        for (Participants participants : user.getParticipants()) {
            if (participants.getParticipantsKey().getParticipantType().getName().equals("moder")) {
                list.add(simpleMapper.toDto(participants.getParticipantsKey().getConference()));
            }
        }
        deleteAllUnnecessaryStreamDTO(list, currentUser);
        return new ListConferenceDTO(list);
    }

    private void deleteAllUnnecessaryStreamDTO(List<ConferenceDTO> list, CurrentUser currentUser) {
        for (ConferenceDTO conferenceDTO : list) {
            conferenceDTO.getStreamList().removeIf(nextDTO -> nextDTO.getModerator() != currentUser.getId());
        }
    }

    @Override
    public ConferenceDTO getConferenceDTOById(Long id) {
        return mapper.toDto(conferenceRepository.findById(id).get());
    }

    @Override
    public ListConferenceDTO getConferencesDTOByOwnerId(Long id) {
        List<Conference> conferenceList = conferenceRepository.findAllByOwnerId(id);
        List<ConferenceDTO> dtos = conferenceList.stream().map(c->simpleMapper.toDto(c)).collect(Collectors.toList());
        return new ListConferenceDTO(dtos);
    }

    @Transactional
    @Override
    public void addNewConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);
        String oldCoverPhotoPath = null;
        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldCoverPhotoPath = conference.getCoverPhoto();
            conference.setCoverPhoto(newCoverPhotoPath);
        }
        conferenceRepository.save(conference);
        if (oldCoverPhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
        }
    }
    
    @Transactional
    @Override
    public void updateConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);
        String oldCoverPhotoPath = null;
        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldCoverPhotoPath = conference.getCoverPhoto();
            conference.setCoverPhoto(newCoverPhotoPath);
        }
        conferenceRepository.save(conference);
        if (oldCoverPhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
        }
        
    }

    @Override
    public void deleteConference(Long confId) {
        Optional<Conference> conferenceOptional = conferenceRepository.findById(confId);
        conferenceRepository.delete(conferenceOptional.get());
    }

private void removeCoverPhotoIfTransactionSuccess(final String oldCoverPhoto) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                imageStorageService.removeOldImage(oldCoverPhoto);
            }
        });
    }
    
    @Override
    public List<ConferenceDTO> getAllConferencesDTO() {
        List<ConferenceDTO> list = new ArrayList<>();
        for (Conference conference : conferenceRepository.findAll()) {
            list.add(simpleMapper.toDto(conference));
        }
        return list;
    }

}
