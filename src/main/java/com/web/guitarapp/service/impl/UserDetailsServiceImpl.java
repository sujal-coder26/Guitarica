package com.web.guitarapp.service.impl;

import com.web.guitarapp.dao.UserRepository;
import com.web.guitarapp.dto.CustomUserDetails;
import com.web.guitarapp.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService { //making the class that implements the following interface

  @Autowired private UserRepository userRepository; // declaring the object

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // overriding the method which will have the

    User user = userRepository.getUserByUserName(username);

    if (user == null) {
      throw new UsernameNotFoundException("Could not find User");
    }
    log.info("Logged in User: {}", user.getUsername());
    return new CustomUserDetails(user);
  }
}
