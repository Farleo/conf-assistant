package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;
    
    @Autowired
    private UserRepository userRepository;

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
    public ListConferenceDTO getAllConferencesDTO() {
        List<ConferenceDTO> list = new ArrayList<>();
        for (Conference conference : conferenceRepository.findAll()) {
            list.add(simpleMapper.toDto(conference));
        }
        return new ListConferenceDTO(list);
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
}
