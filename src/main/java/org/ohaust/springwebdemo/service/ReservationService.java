package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.ohaust.springwebdemo.model.ReservableQuarterHourModel;
import org.ohaust.springwebdemo.model.TimePointModel;
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
        DateModel dateModel = reservationRequest.getDateModel();

        ReservableDateModel dateToReserve = repository.findByDateModel(dateModel);
        if (dateToReserve == null) {
            return new ReservationResult(false, "Date does not exists or not available.");
        }
        TimePointModel timeFrom = reservationRequest.getTimeFrom();
        TimePointModel timeTo = reservationRequest.getTimeTo();
        int amountOfTime = calculateAmountOfTime(timeFrom, timeTo);
        List<ReservableQuarterHourModel> reservableQuarterHourList = dateToReserve.getReservableQuarterHourList();
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

    private boolean reserve(List<ReservableQuarterHourModel> reservableQuarterHourList, int index, int amountOfTime) {

        ReservableQuarterHourModel firstQuarterHour = reservableQuarterHourList.get(index);
        ReservableQuarterHourModel lastQuarterHour = reservableQuarterHourList.get(index + amountOfTime - 1);

        //may be subject to change, there may be edge cases where a quarter hour within range is reserved
        // but not first or last.
        if (firstQuarterHour.isReserved() || lastQuarterHour.isReserved()) {
            return false;
        }

        for (int i = index; i < index + amountOfTime; i++) {
            ReservableQuarterHourModel reservableQuarterHour = reservableQuarterHourList.get(i);
            reservableQuarterHour.setReserved(true);
        }
        return true;
    }

    private int calculateAmountOfTime(TimePointModel timeFrom, TimePointModel timeTo) {
        int hourFrom = timeFrom.getHour();
        int minuteFrom = timeFrom.getMinute();
        int hourTo = timeTo.getHour();
        int minuteTo = timeTo.getMinute();
        return (hourTo - hourFrom) * 4 + (minuteTo - minuteFrom) / 15;
    }


    private int findQuarterHourIndex(List<ReservableQuarterHourModel> reservableQuarterHourList, TimePointModel timeFrom, int amountOfTime) {
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
