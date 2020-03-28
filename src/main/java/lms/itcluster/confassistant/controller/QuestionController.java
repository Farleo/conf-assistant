package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.model.Constant;
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
    private QuestionService questionService;

    @Autowired
    private LikeService likeService;

    @PostMapping(value = "/save-question")
    public boolean saveQuestion(@RequestBody QuestionDTO questionDTO) throws IOException {
        questionDTO = questionService.save(questionDTO);
        return likeService.like(questionDTO.getQuestionId(), questionDTO.getUser());
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
        return likeService.like(questionId, currentUser.getId());
    }

}
