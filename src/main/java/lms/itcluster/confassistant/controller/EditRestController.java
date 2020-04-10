package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.DateDTO;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.QuestionService;
import lms.itcluster.confassistant.service.StaticDataService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
