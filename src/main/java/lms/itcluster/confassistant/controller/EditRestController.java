package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public boolean saveQuestion(@RequestBody QuestionDTO questionDTO) {
        return questionService.saveQuestion(questionDTO);
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

    @GetMapping("/select/{questionId}/{topicId}")
    public boolean selectQuestion(@PathVariable("questionId") Long questionId, @PathVariable("topicId") Long topicId,@AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        StreamDTO streamDTO = streamService.getStreamDTOByName(topicDTO.getStream());
        if (!Objects.equals(streamDTO.getModerator(), currentUser.getId())) {
            return false;
        }
        return questionService.selectNextQuestion(topicDTO.getQuestionListDTO(), questionId);
    }

    @PostMapping(value = "/getDays")
    public List<Integer> getDays(@RequestBody DateDTO dateDTO) {
        return staticDataService.getDays(dateDTO.getYear(), dateDTO.getMonth());
    }

    @PostMapping("/edit/speaker/valid/contacts")
    public ResponseEntity<String> validContacts (@Valid @RequestBody EditContactsDTO contactsDTO) {
        userService.updateUserEmail(contactsDTO);
        return ResponseEntity.ok("User is valid");
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
