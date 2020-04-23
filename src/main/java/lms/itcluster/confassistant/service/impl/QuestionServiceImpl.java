package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.component.CheckEditAccess;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.ForbiddenAccessException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CheckEditAccess checkEditAccess;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("questionMapper")
    private Mapper<Question, QuestionDTO> mapper;

    @Override
    public boolean saveQuestion(QuestionDTO questionDTO, CurrentUser currentUser) {
        Question newQuestion = mapper.toEntity(questionDTO);
        Topic topic = newQuestion.getTopic();
        Long confId = topic.getStream().getConference().getConferenceId();
        if (checkEditAccess.isCurrentUserPresentAtConference(currentUser.getId(), confId)) {
            if (topic.isAllowedQuestion() || checkEditAccess.isActiveTopic(topic)) {
                questionRepository.save(newQuestion);
                return like(newQuestion.getQuestionId(), newQuestion.getUser().getUserId());
            }
            throw new ForbiddenAccessException(String.format("Questions of topic id: %d are not allowed now", topic.getTopicId()));
        }
       throw new ForbiddenAccessException(String.format("Current user with id: %d not registered for the conference with id: %d", currentUser.getId(), confId));
    }

    @Override
    public Question findByName(String name) {
        return questionRepository.findByQuestion(name);
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public List<QuestionDTO> getSortedQuestionDTOListByRating(Long id) {
        List<QuestionDTO> dtoList = getQuestionDTOList(id);
        dtoList.sort((o1, o2) -> o2.getRating() - o1.getRating());
        return dtoList;
    }

    @Override
    public List<QuestionDTO> getSortedQuestionDTOListByDate(Long id) {
        List<QuestionDTO> dtoList = getQuestionDTOList(id);
        dtoList.sort((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()));
        return dtoList;
    }

    @Override
    public boolean selectNextQuestion(List<QuestionDTO> questionDTOList, Long questionId) {
        deselectAndDeletePreviousQuestion(questionDTOList);
        return selectQuestion(questionId);
    }

    private boolean deselectAndDeletePreviousQuestion(List<QuestionDTO> questionDTOList) {
        for (QuestionDTO questionDTO : questionDTOList) {
            if (questionDTO.isSelected()) {
                Question question = questionRepository.findById(questionDTO.getQuestionId()).get();
                question.setSelected(false);
                question.setDeleted(true);
                questionRepository.save(question);
            }
        }
        return true;
    }

    private boolean selectQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).get();
        question.setSelected(true);
        questionRepository.save(question);
        return true;
    }

    private List<QuestionDTO> getQuestionDTOList(Long id) {
        Topic topic = topicRepository.findById(id).get();
        List<QuestionDTO> dtoList = new ArrayList<>();
        topic.getQuestionList().forEach(question -> dtoList.add(mapper.toDto(question)));
        return dtoList;
    }

    @Override
    public boolean like(Long questionId, Long userId) {
        Question question = questionRepository.findById(questionId).get();
        User user = userRepository.findById(userId).get();
        if (question.getLikesSet().contains(user)) {
            return unLike(user, question);
        }
        return addLike(user, question);
    }

    @Override
    public boolean sendQuestionToSpeaker(Long topicId, CurrentUser currentUser) {
        Topic topic = topicService.findById(topicId);
        if (!checkEditAccess.canManageQuestion(currentUser, topic)) {
            throw new ForbiddenAccessException(String.format("Current user with id: %d, can't manage the topic with id: %d", currentUser.getId(), topicId));
        }
        User speaker = userService.findById(topic.getSpeaker().getUserId());
        List<Question> questions = topic.getQuestionList();
        questions.sort(Comparator.comparingInt(Question::getRating));
        StringBuilder letter = new StringBuilder("Here is all your question from topic: " + topic.getName() + "\n");
        int i = 1;
        for (Question question : questions) {
            String answered;
            if (question.isDeleted()) {
                answered = "Answered";
            } else {
                answered = "Not answered";
            }
            letter
                    .append(i)
                    .append(". ")
                    .append(question.getQuestion())
                    .append(" Author - ")
                    .append(question.getUser().getEmail())
                    .append(". ")
                    .append(answered)
                    .append(". Rating: ")
                    .append(question.getRating())
                    .append("\n");
            i++;
        }
        emailService.sendMessage(speaker.getEmail(), "Questions from topic: " + topic.getName(), letter.toString());
        return true;
    }

    private boolean unLike(User user, Question question) {
        user.getLikes().remove(question);
        question.getLikesSet().remove(user);
        userRepository.save(user);
        questionRepository.save(question);
        return false;
    }

    private boolean addLike(User user, Question question) {
        user.getLikes().add(question);
        question.getLikesSet().add(user);
        userRepository.save(user);
        questionRepository.save(question);
        return true;
    }
}
