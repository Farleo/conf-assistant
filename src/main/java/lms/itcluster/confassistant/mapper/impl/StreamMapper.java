package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class StreamMapper extends AbstractMapper<Stream, StreamDTO> {

    @Autowired
    @Qualifier("topicMapper")
    private Mapper<Topic, TopicDTO> mapper;

    private final ModelMapper modelMapper;
    private final ConferenceRepository conferenceRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public StreamMapper(ModelMapper modelMapper, ConferenceRepository conferenceRepository, TopicRepository topicRepository, UserRepository userRepository) {
        super(Stream.class, StreamDTO.class);
        this.modelMapper = modelMapper;
        this.conferenceRepository = conferenceRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Stream.class, StreamDTO.class)
                .addMappings(mapping -> mapping.skip(StreamDTO::setTopicList))
                .addMappings(mapping -> mapping.skip(StreamDTO::setConference))
                .addMappings(mapping -> mapping.skip(StreamDTO::setModerator))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(StreamDTO.class, Stream.class)
                .addMappings(mapping -> mapping.skip(Stream::setTopicList))
                .addMappings(mapping -> mapping.skip(Stream::setConference))
                .addMappings(mapping -> mapping.skip(Stream::setModerator))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Stream source, StreamDTO destination) {
        destination.setConference(source.getConference().getConferenceId());
        destination.setTopicList(setTopicDTO(source));
        destination.setModerator(source.getModerator().getUserId());
    }

    private List<TopicDTO> setTopicDTO(Stream stream) {
        List<TopicDTO> list = new ArrayList<>();
        for (Topic topic : stream.getTopicList()) {
            list.add(mapper.toDto(topic));
        }
        return  list;
    }

    @Override
    protected void mapSpecificFieldsInDto(StreamDTO source, Stream destination) {
        destination.setConference(conferenceRepository.findById(source.getConference()).get());
        destination.setTopicList(setTopic(source));
        destination.setModerator(userRepository.findById(source.getModerator()).get());
    }

    private List<Topic> setTopic(StreamDTO stream) {
        List<Topic> list = new ArrayList<>();
        for (TopicDTO topic : stream.getTopicList()) {
            list.add(topicRepository.findById(topic.getTopicId()).get());
        }
        return  list;
    }
}
