package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.ForbiddenAccessException;
import lms.itcluster.confassistant.exception.NoSuchEntityException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    private CheckDataAccess checkDataAccess;

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

        checkDataAccess.isCurrentUserRegisteredAtConference(currentUser, confId);
        checkDataAccess.isAllowedQuestionAtTopic(topic);

        questionRepository.save(newQuestion);
        return like(newQuestion.getQuestionId(), currentUser);
    }

    @Override
    public Question findByName(String name) {
        return Optional.ofNullable(questionRepository.findByQuestion(name)).orElseThrow(() -> new NoSuchEntityException("Question with name - %s not found." + name));
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Question with id - %d not found." + id));
    }

    @Override
    public List<QuestionDTO> getSortedQuestionDTOListOrderBy(Long topicId, String orderBy, CurrentUser currentUser) {
        Topic topic = topicService.findById(topicId);
        Long confId = topic.getStream().getConference().getConferenceId();

        checkDataAccess.isCurrentUserRegisteredAtConference(currentUser, confId);

        List<QuestionDTO> dtoList = getQuestionDTOList(topicId);
        switch (orderBy) {
            case (Constant.ORDER_BY_DATE):
                dtoList.sort((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()));
                return dtoList;
            case (Constant.ORDER_BY_RATING):
                dtoList.sort((o1, o2) -> o2.getRating() - o1.getRating());
                return dtoList;
            default:
                return dtoList;
        }
    }

    @Override
    @Transactional
    public boolean selectNextQuestion(List<QuestionDTO> questionDTOList, Long questionId) {
        deselectAndDeletePreviousQuestion(questionDTOList);
        return selectQuestion(questionId);
    }

    private void deselectAndDeletePreviousQuestion(List<QuestionDTO> questionDTOList) {
        for (QuestionDTO questionDTO : questionDTOList) {
            if (questionDTO.isSelected()) {
                Question question = questionRepository.findById(questionDTO.getQuestionId()).get();
                question.setSelected(false);
                question.setDeleted(true);
                questionRepository.save(question);
            }
        }
    }

    private boolean selectQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).get();
        question.setSelected(true);
        questionRepository.save(question);
        return true;
    }

    private List<QuestionDTO> getQuestionDTOList(Long id) {
        Topic topic = topicService.findById(id);
        List<QuestionDTO> dtoList = new ArrayList<>();
        topic.getQuestionList().forEach(question -> dtoList.add(mapper.toDto(question)));
        return dtoList;
    }

    @Override
    @Transactional
    public boolean like(Long questionId, CurrentUser currentUser) {
        Question question = findById(questionId);
        Topic topic = topicService.findById(question.getTopic().getTopicId());
        Long confId = topic.getStream().getConference().getConferenceId();

        checkDataAccess.isCurrentUserRegisteredAtConference(currentUser, confId);
        checkDataAccess.isAllowedQuestionAtTopic(topic);

        User user = userService.findById(currentUser.getId());
        if (question.getLikesSet().contains(user)) {
            return unLike(user, question);
        }
        return addLike(user, question);
    }

    @Override
    public boolean sendQuestionToSpeaker(Long topicId, CurrentUser currentUser) {
        Topic topic = topicService.findById(topicId);

        checkDataAccess.canCurrentUserManageTopic(currentUser, topic);

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
        topic.setAllowedQuestion(false);
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
