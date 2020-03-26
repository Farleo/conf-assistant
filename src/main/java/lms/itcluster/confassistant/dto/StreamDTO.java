package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Topic;

import javax.persistence.*;
import java.util.List;

public class StreamDTO {

    private long streamId;
    private String name;
    private Long conference;
    private List<TopicDTO> topicList;

    public StreamDTO() {
    }

    public long getStreamId() {
        return streamId;
    }

    public void setStreamId(long streamId) {
        this.streamId = streamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getConference() {
        return conference;
    }

    public void setConference(Long conference) {
        this.conference = conference;
    }

    public List<TopicDTO> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicDTO> topicList) {
        this.topicList = topicList;
    }
}
