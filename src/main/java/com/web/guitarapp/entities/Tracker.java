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
@Table(name = "TRACKER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Tracker {

    @Column(name = "tracker_id")
    @Id
    private int trackerId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course courseId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "sub_level")
    private int subLevel;

    @Column
    private String status;

    public Tracker(Course courseId, User userId, int subLevel, String status) {
        this.courseId = courseId;
        this.userId = userId;
        this.subLevel = subLevel;
        this.status = status;
    }
}
