package org.ohaust.springwebdemo.service;


import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.model.ReservableQuarterHour;
import org.ohaust.springwebdemo.model.TimePoint;
import org.ohaust.springwebdemo.model.request.AvailabilityRequest;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class AvailabilityService {
    @Autowired
    ReservationRepository reservationRepository;

    public boolean createAnAvailableDay(AvailabilityRequest availabilityRequest) {
        ReservableDate dateFromRequest = transformAvailabilityRequestToReservableDate(availabilityRequest);
        ReservableDate dateInDatabase = reservationRepository.findByDate(availabilityRequest.getDate());
        if (dateInDatabase == null && dateFromRequest != null) {
            reservationRepository.save(dateFromRequest);
            return true;
        } else {
            return false;
        }


    }

    private ReservableDate transformAvailabilityRequestToReservableDate(AvailabilityRequest availabilityRequest) {
        TimePoint timeFrom = availabilityRequest.getTimeFrom();
        TimePoint timeTo = availabilityRequest.getTimeTo();
        List<ReservableQuarterHour> reservableQuarterHourList = new ArrayList<>();
        int amountOfTime = (timeTo.getHour() - timeFrom.getHour()) * 4 + (timeTo.getMinute() - timeFrom.getMinute()) / 15;
        if (amountOfTime < 0) {
            return null;
        }
        for (int i = 0; i < amountOfTime; i++) {
            int hour = timeFrom.getHour() + i / 4;
            int minute = (timeFrom.getMinute() + i * 15) % 60;
            TimePoint timePoint = new TimePoint(hour, minute);
            ReservableQuarterHour quarterHour = new ReservableQuarterHour();
            quarterHour.setStartTime(timePoint);
            reservableQuarterHourList.add(quarterHour);
        }
        return new ReservableDate(availabilityRequest.getDate(), reservableQuarterHourList);
    }
}
