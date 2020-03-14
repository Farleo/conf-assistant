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
}

