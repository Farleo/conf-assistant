package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.entity.Guest;
import lms.itcluster.confassistant.entity.Pojo;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/789")
    public String getUser() {
        List<Question> list = userService.findById(3)
                .getConferenceList()
                .get(0)
                .getStreamList()
                .get(0)
                .getTopicList()
                .get(0)
                .getQuestionList();
        String string = "";
        for (Question question : list) {
            string += question.getQuestion();
        }
        return string;
    }

    @PostMapping("/save")
    public void getGuest(@RequestBody Pojo pojo) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(pojo.getFirstName());
        System.out.println(pojo.getLastName());
    }



}
