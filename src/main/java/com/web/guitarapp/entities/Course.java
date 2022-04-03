package com.web.guitarapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

  @Id
  @Column(name = "course_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int courseId;

  @Column(name = "course_name")
  private String courseName;

  @Column
  private int weightage;

}
