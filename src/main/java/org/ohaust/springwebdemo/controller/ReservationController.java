package org.ohaust.springwebdemo.controller;

import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {
    @Autowired
    ReservationService service;


    /*
    @PostMapping("/reserve")
    public void reserveHour(@RequestBody ReservableDate reservableDate) {
        service.reserveDate(reservableDate);
        System.out.println("Hour" + reservableDate + "successfully reserved");
    }
    */



}
