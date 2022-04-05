package com.web.guitarapp.controller;

import com.web.guitarapp.entities.Tracker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConsumeJsonResponse {
    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> get(){
    return restTemplate.getForEntity("http://localhost:8080/tracker_info/user/{user_id)", Tracker.class, 1);
    }
}
