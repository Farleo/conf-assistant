package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.EditContactsDTO;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.QuestionService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
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
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save-question")
    public boolean saveQuestion(@RequestBody QuestionDTO questionDTO, @AuthenticationPrincipal CurrentUser currentUser) {
        return questionService.saveQuestion(questionDTO, currentUser);
    }

    @GetMapping("/get-all-questions/{topicId}/{orderBy}")
    public List<QuestionDTO> getAllQuestions(@PathVariable("topicId") Long topicId, @PathVariable("orderBy") String orderBy, @AuthenticationPrincipal CurrentUser currentUser) {
        return questionService.getSortedQuestionDTOListOrderBy(topicId, orderBy, currentUser);
    }

    @GetMapping("/like/{questionId}")
    public boolean likeQuestion(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal CurrentUser currentUser) {
        return questionService.like(questionId, currentUser);
    }

    @PostMapping("/edit/profile/contacts")
    public ResponseEntity<String> validContacts(@Valid @RequestBody EditContactsDTO contactsDTO) {
        userService.createActivationCodeForConfirmEmail(contactsDTO);
        return ResponseEntity.ok("User is valid");
    }

    @GetMapping(value = "/question/status/{topicId}")
    public boolean isQuestionAllowed(@PathVariable("topicId") Long topicId) {
        return topicService.isQuestionAllowed(topicId);
    }

    @GetMapping("/manage/topic/select/{questionId}/{topicId}")
    public boolean selectQuestion(@PathVariable("questionId") Long questionId, @PathVariable("topicId") Long topicId, @AuthenticationPrincipal CurrentUser currentUser) {
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
