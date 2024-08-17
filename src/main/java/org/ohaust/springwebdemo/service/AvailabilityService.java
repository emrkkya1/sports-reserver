package org.ohaust.springwebdemo.service;


import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.ohaust.springwebdemo.model.ReservableTimeIntervalModel;
import org.ohaust.springwebdemo.model.TimePointModel;
import org.ohaust.springwebdemo.model.request.AvailableDateDeleteRequest;
import org.ohaust.springwebdemo.model.request.AvailableDateRequest;
import org.ohaust.springwebdemo.model.result.AvailabilityResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    ReservationRepository reservationRepository;

    private static final int INTERVAL_LENGTH = 15;
    private static final int INTERVALS_PER_HOUR = 60 / INTERVAL_LENGTH;

    public AvailabilityResult deleteAvailableDay(AvailableDateDeleteRequest availableDateDeleteRequest) {
        if (availableDateDeleteRequest.getId() == null) {
            return new AvailabilityResult(false, "ID input wasn't available.");
        }
        Optional<ReservableDateModel> reservableDateContainer = reservationRepository.findById(availableDateDeleteRequest.getId());
        if (reservableDateContainer.isEmpty()) {
            return new AvailabilityResult(false, "Invalid ID.");
        }
        reservationRepository.deleteById(availableDateDeleteRequest.getId());
        return new AvailabilityResult(true, "Successfully deleted.");
    }


    public AvailabilityResult updateAvailableDay(AvailableDateRequest availableDateRequest) {
        if (availableDateRequest.getId() == null) {
            return new AvailabilityResult(false, "Invalid date ID.");
        }
        Optional<ReservableDateModel> reservableDateContainer = reservationRepository.findById(availableDateRequest.getId());
        if (reservableDateContainer.isEmpty()) {
            return new AvailabilityResult(false, "There is no such date.");

        } else {

            ReservableDateModel reservableDate = reservableDateContainer.get();
            List<ReservableTimeIntervalModel> reservableTimeSlots = new ArrayList<>();
            boolean isPopulated = populateReservableTimeIntervalList(availableDateRequest.getTimeFrom(), availableDateRequest.getTimeTo(), reservableTimeSlots);
            if (!isPopulated) {
                return new AvailabilityResult(false, "Improper hour input.");
            }
            reservableDate.setReservableQuarterHourList(reservableTimeSlots);
            reservationRepository.save(reservableDate);

            return new AvailabilityResult(true, "Successfully updated availability.");
        }


    }


    public AvailabilityResult createAnAvailableDay(AvailableDateRequest availableDateRequest) {

        if (availableDateRequest.getDateModel() == null || availableDateRequest.getTimeFrom() == null || availableDateRequest.getTimeTo() == null) {
            return new AvailabilityResult(false, "Invalid input");
        }

        ReservableDateModel dateFromRequest = transformAvailabilityRequestToReservableDate(availableDateRequest);
        ReservableDateModel dateInDatabase = reservationRepository.findByDateModel(availableDateRequest.getDateModel());
        if (dateInDatabase == null && dateFromRequest != null) {
            reservationRepository.save(dateFromRequest);
            return new AvailabilityResult(true, "Available day was created and saved to the database successfully.");
        } else {
            return new AvailabilityResult(false, "Date already exists or day-hour values wasn't accepted.");
        }


    }

    private ReservableDateModel transformAvailabilityRequestToReservableDate(AvailableDateRequest availableDateRequest) {
        TimePointModel timeFrom = availableDateRequest.getTimeFrom();
        TimePointModel timeTo = availableDateRequest.getTimeTo();
        List<ReservableTimeIntervalModel> reservableTimeSlots = new ArrayList<>();

        boolean isPopulated = populateReservableTimeIntervalList(timeFrom, timeTo, reservableTimeSlots);
        if (isPopulated) {
            ReservableDateModel reservableDate = new ReservableDateModel(availableDateRequest.getDateModel());
            reservableDate.setReservableQuarterHourList(reservableTimeSlots);
            return reservableDate;
        }
        return null;
    }

    private int numberOfTimeIntervals(TimePointModel timeFrom, TimePointModel timeTo) {
        return (timeTo.getHour() - timeFrom.getHour()) * INTERVALS_PER_HOUR + (timeTo.getMinute() - timeFrom.getMinute()) / INTERVAL_LENGTH;
    }

    private boolean populateReservableTimeIntervalList(TimePointModel timeFrom, TimePointModel timeTo, List<ReservableTimeIntervalModel> reservableTimeSlots) {
        int numberOfTimeIntervals = numberOfTimeIntervals(timeFrom, timeTo);
        if (numberOfTimeIntervals < 0) {
            return false;
        }
        for (int i = 0; i < numberOfTimeIntervals; i++) {
            int hour = timeFrom.getHour() + i / INTERVALS_PER_HOUR;
            int minute = (timeFrom.getMinute() + i * INTERVAL_LENGTH) % 60;
            TimePointModel timePointModel = new TimePointModel(hour, minute);
            ReservableTimeIntervalModel timeIntervalModel = new ReservableTimeIntervalModel();
            timeIntervalModel.setStartTime(timePointModel);
            reservableTimeSlots.add(timeIntervalModel);
        }

        return true;
    }
}


