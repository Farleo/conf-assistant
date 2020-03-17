package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "presentations")
public class Presentations {

@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "presentation_id", unique = true, nullable = false)
private long presentationId;

@Column(name = "name", nullable = false, unique = true)
private String name;

@Column(name = "description", nullable = false, unique = true)
private String description;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "speaker_id")
private User speaker;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "stream_id")
private Stream stream;

@Column(name = "start_time", nullable = false, unique = true)
private Date startTime;

@Column(name = "end_time", nullable = false, unique = true)
private Date endTime;

public long getPresentationId() {
	return presentationId;
}

public void setPresentationId(long presentationId) {
	this.presentationId = presentationId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public User getSpeaker() {
	return speaker;
}

public void setSpeaker(User speaker) {
	this.speaker = speaker;
}

public Stream getStream() {
	return stream;
}

public void setStream(Stream stream) {
	this.stream = stream;
}

public Date getStartTime() {
	return startTime;
}

public void setStartTime(Date startTime) {
	this.startTime = startTime;
}

public Date getEndTime() {
	return endTime;
}

public void setEndTime(Date endTime) {
	this.endTime = endTime;
}
}

