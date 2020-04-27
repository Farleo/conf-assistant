package lms.itcluster.confassistant.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTopicDTO extends AbstractDTO {

	private Long topicId;
	private String name;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate date;
	@DateTimeFormat (pattern="HH:mm")
	private LocalTime beginTime;
	@DateTimeFormat (pattern="HH:mm")
	private LocalTime finishTime;
	@Size(max = 2000, message = "The value must be between 0 and 2000")
	private String info;
	private String coverPhoto;
	private String stream;
	private Long speakerId;
	
	public SimpleTopicDTO() {
	}
	
	public Long getTopicId() {
		return topicId;
	}
	
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getCoverPhoto() {
		return coverPhoto;
	}
	
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	
	public String getStream() {
		return stream;
	}
	
	public void setStream(String stream) {
		this.stream = stream;
	}
	
	public Long getSpeakerId() {
		return speakerId;
	}
	
	public void setSpeakerId(Long speakerId) {
		this.speakerId = speakerId;
	}
	
	public LocalTime getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = beginTime;
	}
	
	public LocalTime getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public Long getId() {
		return topicId;
	}
}
