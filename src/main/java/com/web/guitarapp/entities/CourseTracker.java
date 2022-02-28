package com.web.guitarapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COURSE_TRACKER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseTracker {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int courseId;

  @Column
  private String courseName;

  @Column
  private String courseLevel;

  @Column
  private String courseRegistered;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
