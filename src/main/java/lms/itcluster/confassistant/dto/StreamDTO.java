package lms.itcluster.confassistant.dto;

import java.util.List;

public class StreamDTO extends DTO {

    private Long streamId;
    private String name;
    private Long conference;
    private List<TopicDTO> topicList;
    private Long moderator;

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

    public Long getModerator() {
        return moderator;
    }

    public void setModerator(Long moderator) {
        this.moderator = moderator;
    }

    @Override
    public Long getId() {
        return streamId;
    }
}
