package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.TimeInterval;

import java.util.List;

public class AvailabilityMapper {
    public static DateModel toDateModel(DateInfo dateInfo, List<TimeInterval> timeIntervals) {
        if (dateInfo == null || timeIntervals == null) {
            return null;
        }
        else {
            return new DateModel(dateInfo, timeIntervals);
        }
    }
}
