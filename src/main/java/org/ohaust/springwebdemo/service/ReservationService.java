package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.TimeInterval;
import org.ohaust.springwebdemo.model.TimeModel;
import org.ohaust.springwebdemo.model.request.ReservationRequest;
import org.ohaust.springwebdemo.model.result.ReservationResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {


    private final ReservationRepository repository;


    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public ReservationResult reserveIfAvailable(ReservationRequest reservationRequest) {
        DateModel dateInDatabase = repository.findByDateInfo(reservationRequest.getDateInfo());
        if (dateInDatabase == null) {
            return new ReservationResult(false, "Date does not exist or not available.");
        }
        int index = availableTimeIntervalIndex(reservationRequest);
        if (index == -1) {
            return new ReservationResult(false, "Time interval is not available for reservation.");
        }

        reserve(reservationRequest, index, dateInDatabase.getTimeIntervals());
        repository.save(dateInDatabase);
        return new ReservationResult(true, "Successfully reserved the time interval.");


    }


    private int availableTimeIntervalIndex(ReservationRequest reservationRequest) {
        DateInfo requestedDateInfo = reservationRequest.getDateInfo();
        TimeModel reservationStart = reservationRequest.getReservationStart();
        TimeModel reservationEnd = reservationRequest.getReservationEnd();


        List<TimeInterval> timeIntervals = repository.findByDateInfo(requestedDateInfo).getTimeIntervals();
        for (int i = 0; i < timeIntervals.size(); i++) {
            if (isReservationInInterval(timeIntervals.get(i), reservationStart, reservationEnd)) {
                return i;
            }
        }
        return -1;

    }

    private boolean isReservationInInterval(TimeInterval interval , TimeModel reservationStart, TimeModel reservationEnd) {
        TimeModel intervalStart = interval.getStart();
        TimeModel intervalEnd = interval.getEnd();
        int intervalStartHour = intervalStart.getHour();
        int intervalStartMinute = intervalStart.getMinute();
        int intervalEndHour = intervalEnd.getHour();
        int intervalEndMinute = intervalEnd.getMinute();

        int reservationStartHour = reservationStart.getHour();
        int reservationStartMinute = reservationStart.getMinute();
        int reservationEndHour = reservationEnd.getHour();
        int reservationEndMinute = reservationEnd.getMinute();

        if (reservationStartHour > intervalStartHour || (reservationStartHour == intervalStartHour && reservationStartMinute >= intervalStartMinute)) {
            if (reservationEndHour < intervalEndHour || (reservationEndHour == intervalEndHour && reservationEndMinute <= intervalEndMinute)) {
                return true;
            }
        }
        return false;
    }

    private void reserve(ReservationRequest reservationRequest, int index, List<TimeInterval> timeIntervals) {
        TimeModel reservationStart = reservationRequest.getReservationStart();
        TimeModel reservationEnd = reservationRequest.getReservationEnd();
        TimeInterval interval = timeIntervals.get(index);
        if (reservationStart.equals(interval.getStart())) {
            TimeInterval remainingInterval = new TimeInterval(reservationEnd, interval.getEnd());
            timeIntervals.remove(index);
            if (remainingInterval.getDurationInMinutes() > 0) {
                timeIntervals.add(index, remainingInterval);
            }

        }
        else if (reservationEnd.equals(interval.getEnd())) {
            TimeInterval remainingInterval = new TimeInterval(interval.getStart(), interval.getStart());
            timeIntervals.remove(index);
            if (remainingInterval.getDurationInMinutes() > 0) {
                timeIntervals.add(index, remainingInterval);
            }
        }
        else {
            TimeInterval firstRemainingInterval = new TimeInterval(interval.getStart(), reservationStart);
            TimeInterval secondRemainingInterval = new TimeInterval(reservationEnd, interval.getEnd());
            timeIntervals.remove(index);
            timeIntervals.add(index, firstRemainingInterval);
            timeIntervals.add(index + 1, secondRemainingInterval);
        }

    }




}
