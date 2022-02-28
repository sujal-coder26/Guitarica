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
@Table(name = "ADVANCE_COURSE")
@Getter
@Setter
public class AdvanceCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int advanceCourseId;

  @Column
  private String status;

  @Column
  private int currentAdvanceLevel;

}
