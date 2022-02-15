package com.web.guitarapp.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
