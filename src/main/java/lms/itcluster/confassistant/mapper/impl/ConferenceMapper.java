package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ConferenceMapper extends AbstractMapper<Conference, ConferenceDTO> {

    @Autowired
    @Qualifier("streamMapper")
    private Mapper<Stream, StreamDTO> mapper;

    private final ModelMapper modelMapper;
    private final StreamRepository streamRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConferenceMapper(ModelMapper modelMapper, StreamRepository streamRepository, UserRepository userRepository, ConferenceRepository conferenceRepository) {
        super(ConferenceDTO.class, Conference.class, conferenceRepository);
        this.modelMapper = modelMapper;
        this.streamRepository = streamRepository;
        this.userRepository = userRepository;
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
    }

    private List<StreamDTO> getStreamList(Conference conference) {
        List<StreamDTO> streams = new ArrayList<>();
        for (Stream stream : conference.getStreamList()) {
            streams.add(mapper.toDto(stream));
        }
        return streams;
    }

    private Long getOwner(Conference conference){
        return conference.getOwner().getUserId();
    }
    
    @Override
    protected void mapSpecificFieldsInDto(ConferenceDTO source, Conference destination) {
        destination.setStreamList(getSteamList(source));
        destination.setOwner(getOwner(source));
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
}
