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
@Table(name = "INTERMEDIATE_COURSE")
@Getter
@Setter
@RequiredArgsConstructor
public class IntermediateCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int intermediateCourseId;

  @Column
  private String status;

  @Column
  private int currentIntermediateLevel;

}
