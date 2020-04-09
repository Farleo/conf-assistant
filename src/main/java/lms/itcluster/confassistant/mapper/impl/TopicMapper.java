package lms.itcluster.confassistant.mapper.impl;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
public class TopicMapper extends AbstractMapper<Topic, TopicDTO> {

    @Autowired
    @Qualifier("questionMapper")
    private Mapper<Question, QuestionDTO> questionMapper;

    @Autowired
    @Qualifier("speakerMapper")
    private Mapper<User, SpeakerDTO> speakerMapper;



    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final StreamRepository streamRepository;

    @Autowired
    public TopicMapper(ModelMapper modelMapper, QuestionRepository questionRepository, UserRepository userRepository, StreamRepository streamRepository, TopicRepository topicRepository) {
        super(TopicDTO.class, Topic.class, topicRepository);
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.streamRepository = streamRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Topic.class, TopicDTO.class)
                .addMappings(mapping -> mapping.skip(TopicDTO::setStream))
                .addMappings(mapping -> mapping.skip(TopicDTO::setSpeakerDTO))
                .addMappings(mapping -> mapping.skip(TopicDTO::setQuestionListDTO))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TopicDTO.class, Topic.class)
                .addMappings(mapping -> mapping.skip(Topic::setStream))
                .addMappings(mapping -> mapping.skip(Topic::setSpeaker))
                .addMappings(mapping -> mapping.skip(Topic::setQuestionList))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Topic source, TopicDTO destination) {
        destination.setSpeakerDTO(speakerMapper.toDto(source.getSpeaker()));
        destination.setStream(source.getStream().getName());
        destination.setQuestionListDTO(getQuestions(source));
    }

    private List<QuestionDTO> getQuestions(Topic topic) {
        List<QuestionDTO> list = new ArrayList<>();
        for (Question question : topic.getQuestionList()) {
            list.add(questionMapper.toDto(question));
        }
        return list;
    }

    @Override
    protected void mapSpecificFieldsInDto(TopicDTO source, Topic destination) {
        destination.setSpeaker(userRepository.findByEmail(source.getSpeakerDTO().getEmail()));
        destination.setStream(streamRepository.findByName(source.getStream()));
        destination.setQuestionList(getQuestions(source));
    }

    private List<Question> getQuestions(TopicDTO topicDTO) {
        List<Question> list = new ArrayList<>();
        for (QuestionDTO questionDto : topicDTO.getQuestionListDTO()) {
            list.add(questionRepository.findById(questionDto.getQuestionId()).get());
        }
        return list;
    }
}
