package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

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

    @Override
    public void addNewConference(ConferenceDTO conferenceDTO) {
    Conference conference = mapper.toEntity(conferenceDTO);
    conferenceRepository.save(conference);
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
