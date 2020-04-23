package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class EditRestController {

    @Autowired
    private StaticDataService staticDataService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/save-question")
    public boolean saveQuestion(@RequestBody QuestionDTO questionDTO, @AuthenticationPrincipal CurrentUser currentUser) {
        return questionService.saveQuestion(questionDTO, currentUser);
    }

    @GetMapping("/get-all-questions/{topicId}/{orderBy}")
    public List<QuestionDTO> getAllQuestions(@PathVariable("topicId") Long topicId, @PathVariable("orderBy") String orderBy) {
        if (orderBy.equals(Constant.ORDER_BY_RATING)) {
            return questionService.getSortedQuestionDTOListByRating(topicId);
        }
        else {
            return questionService.getSortedQuestionDTOListByDate(topicId);
        }
    }

    @GetMapping("/like/{questionId}")
    public boolean likeQuestion(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal CurrentUser currentUser) {
        return questionService.like(questionId, currentUser.getId());
    }

    @PostMapping("/edit/profile/contacts")
    public ResponseEntity<String> validContacts (@Valid @RequestBody EditContactsDTO contactsDTO) {
        userService.updateUserEmail(contactsDTO);
        return ResponseEntity.ok("User is valid");
    }

    @GetMapping(value = "/question/status/{topicId}")
    public boolean isQuestionAllowed(@PathVariable("topicId") Long topicId) {
        return topicService.isQuestionAllowed(topicId);
    }

    @GetMapping("/manage/topic/select/{questionId}/{topicId}")
    public boolean selectQuestion(@PathVariable("questionId") Long questionId, @PathVariable("topicId") Long topicId,@AuthenticationPrincipal CurrentUser currentUser) {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId, currentUser);
        StreamDTO streamDTO = streamService.getStreamDTOByName(topicDTO.getStream());
        if (!Objects.equals(streamDTO.getModerator(), currentUser.getId())) {
            return false;
        }
        return questionService.selectNextQuestion(topicDTO.getQuestionListDTO(), questionId);
    }

    @GetMapping("/manage/topic/{topicId}/enable")
    public boolean enable(@PathVariable("topicId") Long topicId, @AuthenticationPrincipal CurrentUser currentUser) {
        return topicService.enableOrDisableQuestion(topicId, currentUser);
    }

}
