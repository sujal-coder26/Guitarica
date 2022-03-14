package com.web.guitarapp.dao;

import com.web.guitarapp.entities.Tracker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public interface CourseTrackerRepository extends CrudRepository<Tracker, Integer> {

  @Query(
      value =
          "SELECT tracker.course_level, c.course_name FROM tracker\n"
              + "JOIN user u on tracker.user_id = u.id\n"
              + "JOIN courses c on c.course_id = tracker.course_id\n"
              + "where u.id = :user_id",
      nativeQuery = true)
  Object getUserLevel(@Param("user_id") int user_id);
}
