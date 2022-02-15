package com.web.guitarapp.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
