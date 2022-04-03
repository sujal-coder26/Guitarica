package com.web.guitarapp.service;

import com.web.guitarapp.entities.Tracker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TrackerService {

    Optional<Tracker> findByUserIdAndStatus(Long userId, String status);

    Tracker save(Tracker tracker);
}