package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(long id) {
        return conferenceRepository.findById(id).get();
    }
}
