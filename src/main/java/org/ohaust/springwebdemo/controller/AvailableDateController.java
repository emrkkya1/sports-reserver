package org.ohaust.springwebdemo.controller;

import org.ohaust.springwebdemo.model.request.AvailableDateDeleteRequest;
import org.ohaust.springwebdemo.model.request.AvailableDateRequest;
import org.ohaust.springwebdemo.model.result.AvailabilityResult;
import org.ohaust.springwebdemo.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
public class AvailableDateController {

    @Autowired
    AvailabilityService availabilityService;

    @PostMapping("/create")
    public ResponseEntity<String> createAvailableDay(@RequestBody AvailableDateRequest availableDateRequest) {
        AvailabilityResult availabilityCreationResult = availabilityService.createAnAvailableDay(availableDateRequest);

        if (availabilityCreationResult.isSuccess()) {
            return ResponseEntity.ok(availabilityCreationResult.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(availabilityCreationResult.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAvailableDay(@RequestBody AvailableDateRequest availableDateUpdateRequest) {
        AvailabilityResult availabilityUpdateResult= availabilityService.updateAvailableDay(availableDateUpdateRequest);
        if (availabilityUpdateResult.isSuccess()) {
            return ResponseEntity.ok(availabilityUpdateResult.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(availabilityUpdateResult.getMessage());
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAvailableDay(@RequestBody AvailableDateDeleteRequest availableDateDeleteRequest) {
        AvailabilityResult availabilityDeleteResult = availabilityService.deleteAvailableDay(availableDateDeleteRequest);
        if (availabilityDeleteResult.isSuccess()) {
            return ResponseEntity.ok(availabilityDeleteResult.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(availabilityDeleteResult.getMessage());
    }
}
