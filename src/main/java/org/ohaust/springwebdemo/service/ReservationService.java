package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.Date;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.model.ReservableQuarterHour;
import org.ohaust.springwebdemo.model.TimePoint;
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
        Date date = reservationRequest.getDate();

        ReservableDate dateToReserve = repository.findByDate(date);
        if (dateToReserve == null) {
            return new ReservationResult(false, "Date does not exists or not available.");
        }
        TimePoint timeFrom = reservationRequest.getTimeFrom();
        TimePoint timeTo = reservationRequest.getTimeTo();
        int amountOfTime = calculateAmountOfTime(timeFrom, timeTo);
        List<ReservableQuarterHour> reservableQuarterHourList = dateToReserve.getReservableQuarterHourList();
        int index = findQuarterHourIndex(reservableQuarterHourList, timeFrom, amountOfTime);
        if (index == -1) {
            return new ReservationResult(false, "Time interval is not available for reservation.");
        }

        if (reserve(reservableQuarterHourList, index, amountOfTime)) {
            repository.save(dateToReserve);
            return new ReservationResult(true, "Successfully reserved the time interval.");

        }
        return new ReservationResult(false, "Time interval or part of time interval already reserved!");
    }

    private boolean reserve(List<ReservableQuarterHour> reservableQuarterHourList, int index, int amountOfTime) {

        ReservableQuarterHour firstQuarterHour = reservableQuarterHourList.get(index);
        ReservableQuarterHour lastQuarterHour = reservableQuarterHourList.get(index + amountOfTime - 1);

        //may be subject to change, there may be edge cases where a quarter hour within range is reserved
        // but not first or last.
        if (firstQuarterHour.isReserved() || lastQuarterHour.isReserved()) {
            return false;
        }

        for (int i = index; i < index + amountOfTime; i++) {
            ReservableQuarterHour reservableQuarterHour = reservableQuarterHourList.get(i);
            reservableQuarterHour.setReserved(true);
        }
        return true;
    }

    private int calculateAmountOfTime(TimePoint timeFrom, TimePoint timeTo) {
        int hourFrom = timeFrom.getHour();
        int minuteFrom = timeFrom.getMinute();
        int hourTo = timeTo.getHour();
        int minuteTo = timeTo.getMinute();
        return (hourTo - hourFrom) * 4 + (minuteTo - minuteFrom) / 15;
    }


    private int findQuarterHourIndex(List<ReservableQuarterHour> reservableQuarterHourList, TimePoint timeFrom, int amountOfTime) {
        int index = -1;
        for (int i = 0; i < reservableQuarterHourList.size(); i++) {
            int hourFrom = reservableQuarterHourList.get(i).getStartTime().getHour();
            if (hourFrom == timeFrom.getHour()) {
                int minuteFrom = reservableQuarterHourList.get(i).getStartTime().getMinute();
                if (minuteFrom == timeFrom.getMinute()) {
                    index = i;
                    break;
                }
            }
        }
        if (index == -1 || index + amountOfTime > reservableQuarterHourList.size()) {
            return -1;
        }
        return index;
    }


}
