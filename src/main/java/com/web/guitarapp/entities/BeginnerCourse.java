package com.web.guitarapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BEGINNER_COURSE")
@Getter
@Setter

public class BeginnerCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int beginnerCourseId;

  @Column
  private String status;

  @Column
  private int currentBeginnerLevel;

}
