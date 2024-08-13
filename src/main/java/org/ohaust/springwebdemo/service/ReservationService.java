package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.Date;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.model.ReservableHour;
import org.ohaust.springwebdemo.model.request.ReservationRequest;
import org.ohaust.springwebdemo.model.result.ReservationResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;


    public ReservationService(final ReservationRepository repository) {
        this.repository = repository;
    }

    public ReservationResult reserveIfAvailable(final ReservationRequest reservationRequest) {
        Date date = reservationRequest.getDate();

        ReservableDate dateToReserve = repository.findByDate(date);
        if (dateToReserve == null) {
            return new ReservationResult(false, "Date does not exists or not available.");
        }
        int hour = reservationRequest.getHour();
        List<ReservableHour> reservableHourList = dateToReserve.getReservableHourList();
        int index = findIndexOfHour(reservableHourList, hour);
        if (index == -1) {
            return new ReservationResult(false, "Hour is not available for reservation.");
        }
        ReservableHour reservableHour = reservableHourList.get(index);
        if (reserve(reservableHour)) {
            repository.save(dateToReserve);
            return new ReservationResult(true, "Successfully reserved hour.");

        }
        return new ReservationResult(false, "Hour already reserved!");
    }

    public boolean reserve(final ReservableHour reservableHour) {
        if (reservableHour.isReserved()) {
            return false;
        }
        reservableHour.setReserved(true);
        return true;
    }


    private int findIndexOfHour(final List<ReservableHour> reservableHourList, final int hour) {
        for (int i = 0; i < reservableHourList.size(); i++) {
            int hourOfDay = reservableHourList.get(i).getHour();
            if (hour == hourOfDay) {
                return i;
            }
        }
        return -1;
    }


}
