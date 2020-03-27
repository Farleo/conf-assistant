package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class QuestionMapper extends AbstractMapper<Question, QuestionDTO> {


    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuestionMapper(ModelMapper modelMapper, TopicRepository topicRepository, UserRepository userRepository) {
        super(Question.class, QuestionDTO.class);
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Question.class, QuestionDTO.class)
                .addMappings(mapping -> mapping.skip(QuestionDTO::setLikesSet))
                .addMappings(mapping -> mapping.skip(QuestionDTO::setTopic))
                .addMappings(mapping -> mapping.skip(QuestionDTO::setUser)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(QuestionDTO.class, Question.class)
                .addMappings(mapping -> mapping.skip(Question::setLikesSet))
                .addMappings(mapping -> mapping.skip(Question::setTopic))
                .addMappings(mapping -> mapping.skip(Question::setUser)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(Question source, QuestionDTO destination) {
        destination.setTopic(getTopicId(source));
        destination.setUser(getUserId(source));
        destination.setLikesSet(getLikeSet(source));
    }

    private Long getTopicId(Question question) {
        return Objects.isNull(question) || Objects.isNull(question.getTopic()) ? null : question.getTopic().getTopicId();
    }

    private Long getUserId(Question question) {
        return Objects.isNull(question) || Objects.isNull(question.getUser()) ? null : question.getUser().getUserId();
    }

    private Set<Long> getLikeSet(Question question) {
        Set<Long> likeSet = new HashSet<>();
        for (User user : question.getLikesSet()) {
            likeSet.add(user.getUserId());
        }
        return likeSet;
    }

    @Override
    protected void mapSpecificFieldsInDto(QuestionDTO source, Question destination) {
        destination.setTopic(topicRepository.findById(source.getTopic()).orElse(null));
        destination.setUser(userRepository.findById(source.getUser()).orElse(null));
        destination.setLikesSet(setLikeSet(source));
    }

    private Set<User> setLikeSet(QuestionDTO question) {
        Set<User> userSet = new HashSet<>();
        if (question.getLikesSet() != null) {
            for (Long Long : question.getLikesSet()) {
                userSet.add(userRepository.findById(Long).get());
            }
        }
        return userSet;
    }
}
