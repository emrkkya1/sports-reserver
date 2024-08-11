package org.ohaust.springwebdemo.controller;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.model.request.ReservationRequest;
import org.ohaust.springwebdemo.model.result.ReservationResult;
import org.ohaust.springwebdemo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody ReservationRequest reservationRequest) {
        ReservationResult reservationResult = reservationService.reserveIfAvailable(reservationRequest);
        if (reservationResult.isSuccess()) {
            return ResponseEntity.ok(reservationResult.getMessage());
        }
        return ResponseEntity.badRequest().body(reservationResult.getMessage());
    }

}
