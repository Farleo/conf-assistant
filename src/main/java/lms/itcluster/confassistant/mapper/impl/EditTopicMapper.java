package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class EditTopicMapper extends AbstractMapper<Topic, EditTopicDTO> {

    private final ModelMapper modelMapper;

    private TopicRepository topicRepository;

    @Autowired
    public EditTopicMapper(ModelMapper modelMapper, TopicRepository topicRepository) {
        super(EditTopicDTO.class, Topic.class, topicRepository);
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Topic.class, EditTopicDTO.class);
        modelMapper.createTypeMap(EditTopicDTO.class, Topic.class)
                .addMappings(mapping -> mapping.skip(Topic::setDate))
                .addMappings(mapping -> mapping.skip(Topic::setBeginTime))
                .addMappings(mapping -> mapping.skip(Topic::setFinishTime))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Topic source, EditTopicDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(EditTopicDTO source, Topic destination) {
        destination.setDate(LocalDate.of(source.getYear(), source.getMonth(), source.getDay()));
        destination.setBeginTime(LocalTime.of(source.getBeginHour(), source.getBeginMinuets(), 0));
        destination.setFinishTime(LocalTime.of(source.getFinishHour(), source.getFinishHour(), 0));
    }

}
