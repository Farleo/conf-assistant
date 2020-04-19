package lms.itcluster.confassistant.dto;

import java.io.Serializable;
import java.util.List;

public class ListConferenceDTO implements Serializable {
    private List<ConferenceDTO> conferenceList;

    public ListConferenceDTO(List<ConferenceDTO> conferenceList) {
        super();
        this.conferenceList = conferenceList;
    }

    public ListConferenceDTO() {
    }

    public List<ConferenceDTO> getConferenceList() {
        return conferenceList;
    }

    public void setConferenceList(List<ConferenceDTO> conferenceList) {
        this.conferenceList = conferenceList;
    }
}
