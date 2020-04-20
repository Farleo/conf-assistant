package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.StreamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EditTopicMapper extends AbstractMapper<Topic, EditTopicDTO> {

    private final ModelMapper modelMapper;

    private TopicRepository topicRepository;

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    public EditTopicMapper(ModelMapper modelMapper, TopicRepository topicRepository) {
        super(EditTopicDTO.class, Topic.class, topicRepository);
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Topic.class, EditTopicDTO.class)
                .addMappings(mapping -> mapping.skip(EditTopicDTO::setBeginDateTime))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(EditTopicDTO.class, Topic.class)
                .addMappings(mapping -> mapping.skip(Topic::setDate))
                .addMappings(mapping -> mapping.skip(Topic::setBeginTime))
                .addMappings(mapping -> mapping.skip(Topic::setFinishTime))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Topic source, EditTopicDTO destination) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        destination.setBeginDateTime(LocalDateTime.of(source.getDate(), source.getBeginTime()).format(dateTimeFormatter));
    }

    @Override
    protected void mapSpecificFieldsInDto(EditTopicDTO source, Topic destination) {
        destination.setDate(LocalDateTime.parse(source.getBeginDateTime()).toLocalDate());
        destination.setBeginTime(LocalDateTime.parse(source.getBeginDateTime()).toLocalTime());
        destination.setFinishTime(LocalTime.parse(source.getFinishTime()));
    }

}
