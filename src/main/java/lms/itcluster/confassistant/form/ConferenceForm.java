package lms.itcluster.confassistant.form;

import lms.itcluster.confassistant.entity.Conference;

import java.io.Serializable;
import java.util.List;

public class ConferenceForm implements Serializable {
    private List<Conference> conferenceList;

    public ConferenceForm(List<Conference> conferenceList) {
        super();
        this.conferenceList = conferenceList;
    }

    public ConferenceForm() {
    }

    public List<Conference> getConferenceList() {
        return conferenceList;
    }

    public void setConferenceList(List<Conference> conferenceList) {
        this.conferenceList = conferenceList;
    }
}
