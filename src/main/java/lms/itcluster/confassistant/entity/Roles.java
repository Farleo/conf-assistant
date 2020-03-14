package lms.itcluster.confassistant.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "role_id", unique = true, nullable = false)
private long roleId;

@Column(name = "name", nullable = false, unique = true)
private String name;

@Column(name = "description", nullable = false, unique = true)
private String description;

@ManyToMany(mappedBy = "roles")
private List<User> users = new ArrayList<>();



}
