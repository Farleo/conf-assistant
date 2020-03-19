package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Conference;

import java.util.List;

public interface ConferenceService {

    List<Conference> getAllConferences();

    Conference findById(long id);
}
