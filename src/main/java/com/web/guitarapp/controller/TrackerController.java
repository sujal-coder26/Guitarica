package com.web.guitarapp.controller;

import com.web.guitarapp.service.TrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrackerController {

  private final TrackerService trackerService;

  @GetMapping(value = "/tracker_info/user/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getTrackerInfo(@PathVariable("user_id") Long userId) {
    return new ResponseEntity<>(
        trackerService.findByUserIdAndStatus(userId, "ACTIVE"), HttpStatus.OK);
  }

}
