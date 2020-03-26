package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
public class QuestionController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @PostMapping(value = "/save-question/{topicId}")
    public boolean postCustomer(@PathVariable("topicId") Long topicId, @RequestBody QuestionDTO questionDTO, @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        User user = userService.findById(currentUser.getId());
        Topic topic = topicService.findById(topicId);
        Question question = questionService.save(questionDTO, user, topic);
        return likeService.like(user, question);
    }

    @GetMapping("/get-all-questions/{topicId}")
    public List<QuestionDTO> getQuestions(@PathVariable("topicId") Long topicId) {
        Topic topic = topicService.findById(topicId);
        return questionService.getSortedQuestionDTOList(topic);
    }

    @GetMapping("/like/{questionId}")
    public boolean getLike(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal CurrentUser currentGuest) {
        Question question = questionService.findById(questionId);
        User user = userService.findById(currentGuest.getId());
        return likeService.like(user, question);
    }

}
