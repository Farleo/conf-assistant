package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.DateDTO;
import lms.itcluster.confassistant.service.StaticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EditRestController {

    @Autowired
    private StaticDataService staticDataService;

    @PostMapping(value = "/getDays")
    public List<Integer> getDays(@RequestBody DateDTO dateDTO) {
        return staticDataService.getDays(dateDTO.getYear(), dateDTO.getMonth());
    }
}
