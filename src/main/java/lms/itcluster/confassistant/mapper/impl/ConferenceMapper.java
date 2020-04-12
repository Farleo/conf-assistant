package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class ConferenceMapper extends AbstractMapper<Conference, ConferenceDTO> {

    @Autowired
    @Qualifier("streamMapper")
    private Mapper<Stream, StreamDTO> mapper;

    private final ModelMapper modelMapper;
    private final StreamRepository streamRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public ConferenceMapper(ModelMapper modelMapper, StreamRepository streamRepository, UserRepository userRepository, ConferenceRepository conferenceRepository, ParticipantRepository participantRepository) {
        super(ConferenceDTO.class, Conference.class, conferenceRepository);
        this.modelMapper = modelMapper;
        this.streamRepository = streamRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Conference.class, ConferenceDTO.class)
                .addMappings(mapping -> mapping.skip(ConferenceDTO::setOwner))
                .addMappings(mapping -> mapping.skip(ConferenceDTO::setStreamList))
                .addMappings(mapping -> mapping.skip(ConferenceDTO::setParticipants)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ConferenceDTO.class, Conference.class)
                .addMappings(mapping -> mapping.skip(Conference::setOwner))
                .addMappings(mapping -> mapping.skip(Conference::setStreamList))
                .addMappings(mapping -> mapping.skip(Conference::setParticipants)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Conference source, ConferenceDTO destination) {
        destination.setStreamList(getStreamList(source));
        destination.setOwner(getOwner(source));
        destination.setParticipants(getParticipantList(source));
    }

    private List<StreamDTO> getStreamList(Conference conference) {
        List<StreamDTO> streams = new ArrayList<>();
        for (Stream stream : conference.getStreamList()) {
            streams.add(mapper.toDto(stream));
        }
        return streams;
    }
    
    private List<Long> getParticipantList(Conference conference){
        return conference.getParticipants().stream().map(p->p.getParticipantsKey().getUser().getUserId()).distinct().collect(Collectors.toList());
    }

    private Long getOwner(Conference conference){
        return conference.getOwner().getUserId();
    }
    
    @Override
    protected void mapSpecificFieldsInDto(ConferenceDTO source, Conference destination) {
        destination.setStreamList(getSteamList(source));
        destination.setOwner(getOwner(source));
        destination.setParticipants(getParticipantList(source));
    }

    private List<Stream> getSteamList(ConferenceDTO conferenceDTO) {
        List<Stream> streams = new ArrayList<>();
        for (StreamDTO streamDTO : conferenceDTO.getStreamList()) {
            streams.add(streamRepository.findById(streamDTO.getStreamId()).get());
        }
        return streams;
    }
    
    private User getOwner(ConferenceDTO conferenceDTO){
        return userRepository.findById(conferenceDTO.getOwner()).get();
    }
    
    private List<Participants> getParticipantList(ConferenceDTO conferenceDTO){
        List<Participants> participantsList = new ArrayList<>();
        for(Long participantId : conferenceDTO.getParticipants()){
            participantsList.addAll(participantRepository.findByUserIdAndConfId(participantId,conferenceDTO.getConferenceId()));
        }
        return participantsList;
    }
}
