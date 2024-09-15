package org.ohaust.springwebdemo.service;


import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.TimeInterval;
import org.ohaust.springwebdemo.model.request.AvailabilityRequest;
import org.ohaust.springwebdemo.model.result.DayCreationResult;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    ReservationRepository reservationRepository;


    public DayCreationResult createAnAvailableDay(AvailabilityRequest availabilityRequest) {
        DateInfo requestedDateInfo = availabilityRequest.getDateInfo();
        List<TimeInterval> requestedIntervals = availabilityRequest.getTimeIntervals();
        DateModel requestedDate = AvailabilityMapper.toDateModel(requestedDateInfo, requestedIntervals);
        DateModel dateInDatabase = reservationRepository.findByDateInfo(requestedDateInfo);
        if (dateInDatabase == null && requestedDate != null) {
            reservationRepository.save(requestedDate);
            return new DayCreationResult(true, "Available day was created and saved to the database successfully.");
        } else {
            return new DayCreationResult(false, "Date already exists or day-hour values wasn't accepted.");
        }


    }


}


