package org.ohaust.springwebdemo.service;


import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.ohaust.springwebdemo.model.ReservableTimeIntervalModel;
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

    private static final int intervalLength = 15;
    private final int intervalsPerHour = 60 / intervalLength;

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
        List<ReservableTimeIntervalModel> reservableTimeIntervalModelList = new ArrayList<>();
        int numberOfTimeIntervals = numberOfTimeIntervals(timeFrom, timeTo);
        if (numberOfTimeIntervals < 0) {
            return null;
        }
        populateReservableTimeIntervalList(timeFrom,numberOfTimeIntervals,reservableTimeIntervalModelList);
        return new ReservableDateModel(availabilityRequest.getDateModel(), reservableTimeIntervalModelList);
    }

    private int numberOfTimeIntervals(TimePointModel timeFrom, TimePointModel timeTo) {
        return (timeTo.getHour() - timeFrom.getHour()) * intervalsPerHour + (timeTo.getMinute() - timeFrom.getMinute()) / intervalLength;
    }

    private void populateReservableTimeIntervalList(TimePointModel timeFrom, int numberOfTimeIntervals,List<ReservableTimeIntervalModel> reservableTimeIntervalModelList) {
        for (int i = 0; i < numberOfTimeIntervals; i++) {
            int hour = timeFrom.getHour() + i / intervalsPerHour;
            int minute = (timeFrom.getMinute() + i * intervalLength) % 60;
            TimePointModel timePointModel = new TimePointModel(hour, minute);
            ReservableTimeIntervalModel timeIntervalModel = new ReservableTimeIntervalModel();
            timeIntervalModel.setStartTime(timePointModel);
            reservableTimeIntervalModelList.add(timeIntervalModel);
        }
    }
}


