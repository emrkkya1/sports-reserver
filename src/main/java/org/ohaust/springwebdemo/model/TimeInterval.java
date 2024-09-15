package org.ohaust.springwebdemo.model;

import lombok.Data;

@Data

public class TimeInterval {
    private TimeModel start;
    private TimeModel end;

    public TimeInterval(TimeModel start, TimeModel end) {
        int startHour = start.getHour();
        int startMinute = start.getMinute();
        int endHour = end.getHour();
        int endMinute = end.getMinute();
        if (startHour > endHour || (startHour == endHour && startMinute > endMinute)) {
            throw new IllegalArgumentException("Interval duration can't be negative.");
        }

        this.start = new TimeModel(startHour, startMinute);
        this.end = new TimeModel(endHour, endMinute);
    }

    public int getDurationInMinutes() {
        return (end.getHour() - start.getHour()) * 60 + (end.getMinute() - start.getMinute());
    }
}
