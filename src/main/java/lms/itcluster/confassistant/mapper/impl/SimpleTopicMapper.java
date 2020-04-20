package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SimpleTopicMapper extends AbstractMapper<Topic, SimpleTopicDTO> {
	
	@Autowired
	@Qualifier("speakerMapper")
	private Mapper<User, SpeakerDTO> speakerMapper;
	
	
	
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final StreamRepository streamRepository;
	
	@Autowired
	public SimpleTopicMapper(ModelMapper modelMapper, UserRepository userRepository, StreamRepository streamRepository, TopicRepository topicRepository) {
		super(SimpleTopicDTO.class, Topic.class, topicRepository);
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.streamRepository = streamRepository;
	}
	
	@PostConstruct
	public void setupMapper() {
		modelMapper.createTypeMap(Topic.class, SimpleTopicDTO.class)
				.addMappings(mapping -> mapping.skip(SimpleTopicDTO::setStream))
				.setPostConverter(toDtoConverter());
		modelMapper.createTypeMap(SimpleTopicDTO.class, Topic.class)
				.addMappings(mapping -> mapping.skip(Topic::setStream))
				.setPostConverter(toEntityConverter());
	}
	
	@Override
	protected void mapSpecificFieldsInEntity(Topic source, SimpleTopicDTO destination) {
		destination.setSpeakerId(source.getSpeaker().getUserId());
		destination.setStream(source.getStream().getName());
	}
	
	@Override
	protected void mapSpecificFieldsInDto(SimpleTopicDTO source, Topic destination) {
		destination.setSpeaker(userRepository.findById(source.getSpeakerId()).get());
		destination.setStream(streamRepository.findByName(source.getStream()));
	}

}
