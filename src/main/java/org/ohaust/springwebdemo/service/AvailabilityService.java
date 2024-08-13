package org.ohaust.springwebdemo.service;


import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final ReservationRepository reservationRepository;

    public boolean createAnAvailableDay(final ReservableDate reservableDate) {
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
