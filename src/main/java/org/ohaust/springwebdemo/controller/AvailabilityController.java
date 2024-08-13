package org.ohaust.springwebdemo.controller;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;


    @PostMapping("/create")
    public ResponseEntity<String> createAvailableDay(@RequestBody final ReservableDate reservableDate) {
        boolean isDayCreated = availabilityService.createAnAvailableDay(reservableDate);

        if (isDayCreated) {
            return ResponseEntity.ok("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Day already exists..");
        }
    }
}
