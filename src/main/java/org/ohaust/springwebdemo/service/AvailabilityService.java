package org.ohaust.springwebdemo.service;


import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.ohaust.springwebdemo.model.ReservableQuarterHourModel;
import org.ohaust.springwebdemo.model.TimePointModel;
import org.ohaust.springwebdemo.model.request.AvailabilityRequest;
import org.ohaust.springwebdemo.model.result.DayCreationResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class AvailabilityService {
    @Autowired
    ReservationRepository reservationRepository;

    public DayCreationResult createAnAvailableDay(AvailabilityRequest availabilityRequest) {
        ReservableDateModel dateFromRequest = transformAvailabilityRequestToReservableDate(availabilityRequest);
        ReservableDateModel dateInDatabase = reservationRepository.findByDateModel(availabilityRequest.getDateModel());
        if (dateInDatabase == null && dateFromRequest != null) {
            reservationRepository.save(dateFromRequest);
            return new DayCreationResult(true,"Available day was created and saved to the database successfully.");
        } else {
            return new DayCreationResult(false,"Date already exists or day-hour values wasn't accepted.");
        }


    }

    private ReservableDateModel transformAvailabilityRequestToReservableDate(AvailabilityRequest availabilityRequest) {
        TimePointModel timeFrom = availabilityRequest.getTimeFrom();
        TimePointModel timeTo = availabilityRequest.getTimeTo();
        List<ReservableQuarterHourModel> reservableQuarterHourList = new ArrayList<>();
        int amountOfTime = (timeTo.getHour() - timeFrom.getHour()) * 4 + (timeTo.getMinute() - timeFrom.getMinute()) / 15;
        if (amountOfTime < 0) {
            return null;
        }
        for (int i = 0; i < amountOfTime; i++) {
            int hour = timeFrom.getHour() + i / 4;
            int minute = (timeFrom.getMinute() + i * 15) % 60;
            TimePointModel timePointModel = new TimePointModel(hour, minute);
            ReservableQuarterHourModel quarterHour = new ReservableQuarterHourModel();
            quarterHour.setStartTime(timePointModel);
            reservableQuarterHourList.add(quarterHour);
        }
        return new ReservableDateModel(availabilityRequest.getDateModel(), reservableQuarterHourList);
    }
}
