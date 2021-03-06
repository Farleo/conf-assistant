package lms.itcluster.confassistant.dto;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class StreamDTO extends AbstractDTO {

    private Long streamId;
    private String name;
    private Long conference;
    private List<TopicDTO> topicList = new ArrayList<>();
    private Long moderator;
    private String random = RandomStringUtils.randomAlphabetic(10);

    public StreamDTO() {
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
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
