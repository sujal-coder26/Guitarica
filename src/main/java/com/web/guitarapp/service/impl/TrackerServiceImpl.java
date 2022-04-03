package com.web.guitarapp.service.impl;

import com.web.guitarapp.dao.TrackerRepository;
import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.Tracker;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.service.TrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackerServiceImpl implements TrackerService {

  private final TrackerRepository trackerRepository;
  private final UserRepository userRepository;

  @Override
  public Tracker save(Tracker tracker) {
    return trackerRepository.save(tracker);
  }

  @Override
  public Optional<Tracker> findByUserIdAndStatus(Long userId, String status) {
    User user = userRepository.getUserById(userId);
    return trackerRepository.findByUserIdAndStatus(user, status);
  }
}
