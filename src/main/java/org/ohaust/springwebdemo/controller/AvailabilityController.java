package org.ohaust.springwebdemo.controller;

import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    @PostMapping("/create")
    public ResponseEntity<String> createAvailableDay(@RequestBody ReservableDate reservableDate) {
        boolean isDayCreated = availabilityService.createAnAvailableDay(reservableDate);

        if (isDayCreated) {
            return ResponseEntity.ok("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Day already exists..");
        }
    }
}
