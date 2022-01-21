package com.web.guitarapp.service.impl;

import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.entities.User;
import com.web.guitarapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final BCryptPasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  @Override
  public void save(User user) {
    user.setRole("USER");
    user.setEnabled(true);
    user.setImageurl("image1.png");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }
}
