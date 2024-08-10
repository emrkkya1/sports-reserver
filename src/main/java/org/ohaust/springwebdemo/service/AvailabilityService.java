package org.ohaust.springwebdemo.service;

import org.apache.coyote.Response;
import org.ohaust.springwebdemo.model.Date;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {
    @Autowired
    ReservationRepository reservationRepository;

    public boolean createAnAvailableDay(ReservableDate reservableDate) {
        ReservableDate dateInDatabase = reservationRepository.findByDate(reservableDate.getDate());
        if (dateInDatabase == null) {
            reservationRepository.save(reservableDate);
            return true;
        }
        else {
            return false;
        }


    }
}
