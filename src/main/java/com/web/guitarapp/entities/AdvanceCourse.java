package com.web.guitarapp.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
