package com.web.guitarapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COURSES")
@Getter
@Setter
@RequiredArgsConstructor
public class Courses {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int course_id;

  @Column
  private String course_name;

  @Column
  private int weightage;

}
