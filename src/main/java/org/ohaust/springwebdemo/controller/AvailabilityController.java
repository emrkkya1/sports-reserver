package org.ohaust.springwebdemo.controller;

import org.ohaust.springwebdemo.model.request.AvailabilityRequest;
import org.ohaust.springwebdemo.model.result.DayCreationResult;
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
    public ResponseEntity<String> createAvailableDay(@RequestBody AvailabilityRequest availabilityRequest) {
        DayCreationResult dayCreationResult = availabilityService.createAnAvailableDay(availabilityRequest);

        if (dayCreationResult.isSuccess()) {
            return ResponseEntity.ok(dayCreationResult.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dayCreationResult.getMessage());
        }
    }
}
