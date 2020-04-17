package lms.itcluster.confassistant.dto;

import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Time;
import java.time.LocalDate;

public class SimpleTopicDTO extends AbstractDTO {

	private Long topicId;
	private String name;
	@DateTimeFormat (pattern="dd-MM-yyyy")
	private LocalDate date;
	private Time beginTime;
	private Time finishTime;
	private int beginHour;
	private int beginMinuets;
	private int finishHour;
	private int finishMinuets;
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
	
	public int getBeginHour() {
		return beginHour;
	}
	
	public void setBeginHour(int beginHour) {
		this.beginHour = beginHour;
	}
	
	public int getBeginMinuets() {
		return beginMinuets;
	}
	
	public void setBeginMinuets(int beginMinuets) {
		this.beginMinuets = beginMinuets;
	}
	
	public int getFinishHour() {
		return finishHour;
	}
	
	public void setFinishHour(int finishHour) {
		this.finishHour = finishHour;
	}
	
	public int getFinishMinuets() {
		return finishMinuets;
	}
	
	public void setFinishMinuets(int finishMinuets) {
		this.finishMinuets = finishMinuets;
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
	
	public Time getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}
	
	public Time getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Time finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public Long getId() {
		return topicId;
	}
}
