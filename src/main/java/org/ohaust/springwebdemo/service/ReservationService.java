package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.ohaust.springwebdemo.model.ReservableTimeIntervalModel;
import org.ohaust.springwebdemo.model.TimePointModel;
import org.ohaust.springwebdemo.model.request.ReservationRequest;
import org.ohaust.springwebdemo.model.result.ReservationResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private static final int INTERVAL_LENGTH = 15;
    private static final int INTERVALS_PER_HOUR = 60 / INTERVAL_LENGTH;
    private final ReservationRepository repository;


    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public ReservationResult reserveIfAvailable(ReservationRequest reservationRequest) {
        DateModel dateModel = reservationRequest.getDateModel();

        ReservableDateModel dateToReserve = repository.findByDateModel(dateModel);
        if (dateToReserve == null) {
            return new ReservationResult(false, "Date does not exists or not available.");
        }
        TimePointModel timeFrom = reservationRequest.getTimeFrom();
        TimePointModel timeTo = reservationRequest.getTimeTo();
        int numberOfIntervals = calculateNumberOfIntervals(timeFrom, timeTo);
        List<ReservableTimeIntervalModel> reservableTimeSlots = dateToReserve.getReservableQuarterHourList();
        int index = findTimeIntervalIndex(reservableTimeSlots, timeFrom, numberOfIntervals);
        if (index == -1) {
            return new ReservationResult(false, "Time interval is not available for reservation.");
        }

        if (reserve(reservableTimeSlots, index, numberOfIntervals)) {
            repository.save(dateToReserve);
            return new ReservationResult(true, "Successfully reserved the time interval.");

        }
        return new ReservationResult(false, "Time interval or part of time interval already reserved!");
    }

    private boolean reserve(List<ReservableTimeIntervalModel> reservableTimeSlots, int index, int amountOfTime) {

        ReservableTimeIntervalModel firstTimeInterval = reservableTimeSlots.get(index);
        ReservableTimeIntervalModel lastTimeInterval = reservableTimeSlots.get(index + amountOfTime - 1);

        //may be subject to change, there may be edge cases where a quarter hour within range is reserved
        // but not first or last.
        if (firstTimeInterval.isReserved() || lastTimeInterval.isReserved()) {
            return false;
        }

        for (int i = index; i < index + amountOfTime; i++) {
            ReservableTimeIntervalModel reservableTimeIntervalModel = reservableTimeSlots.get(i);
            reservableTimeIntervalModel.setReserved(true);
        }
        return true;
    }

    private int calculateNumberOfIntervals(TimePointModel timeFrom, TimePointModel timeTo) {
        int hourFrom = timeFrom.getHour();
        int minuteFrom = timeFrom.getMinute();
        int hourTo = timeTo.getHour();
        int minuteTo = timeTo.getMinute();
        return (hourTo - hourFrom) * INTERVALS_PER_HOUR + (minuteTo - minuteFrom) / INTERVAL_LENGTH;
    }


    private int findTimeIntervalIndex(List<ReservableTimeIntervalModel> reservableTimeSlots, TimePointModel timeFrom, int numberOfTimeIntervals) {
        int index = -1;
        for (int i = 0; i < reservableTimeSlots.size(); i++) {
            int hourFrom = reservableTimeSlots.get(i).getStartTime().getHour();
            if (hourFrom == timeFrom.getHour()) {
                int minuteFrom = reservableTimeSlots.get(i).getStartTime().getMinute();
                if (minuteFrom == timeFrom.getMinute()) {
                    index = i;
                    break;
                }
            }
        }
        if (index == -1 || index + numberOfTimeIntervals > reservableTimeSlots.size()) {
            return -1;
        }
        return index;
    }


}
