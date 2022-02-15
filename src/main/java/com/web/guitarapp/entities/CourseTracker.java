package com.web.guitarapp.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "COURSE_TRACKER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseTracker{
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
   @JoinColumn(name="user_id", referencedColumnName = "id")
   private User user;
}
