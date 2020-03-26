package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpeakerMapper extends AbstractMapper<User, SpeakerDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public SpeakerMapper(ModelMapper modelMapper) {
        super(User.class, SpeakerDTO.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, SpeakerDTO.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(SpeakerDTO.class, User.class)
                .addMappings(mapping -> mapping.skip(User::setPassword))
                .addMappings(mapping -> mapping.skip(User::setTopicList))
                .addMappings(mapping -> mapping.skip(User::setQuestionList))
                .addMappings(mapping -> mapping.skip(User::setLikes))
                .addMappings(mapping -> mapping.skip(User::setRoles))
                .addMappings(mapping -> mapping.skip(User::setUserId))
                .addMappings(mapping -> mapping.skip(User::setParticipants))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, SpeakerDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(SpeakerDTO source, User destination) {
    }

}
