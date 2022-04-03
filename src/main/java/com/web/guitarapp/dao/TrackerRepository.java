package com.web.guitarapp.dao;

import com.web.guitarapp.entities.Tracker;
import com.web.guitarapp.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackerRepository extends CrudRepository<Tracker, Integer> {

  Optional<Tracker> findByUserIdAndStatus(User user, String status);
}
