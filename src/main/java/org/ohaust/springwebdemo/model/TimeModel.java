package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeModel {
    private int hour;
    private int minute;
    private int minuteStep = 15;

    public TimeModel(int hour, int minute) {
        if (minute % minuteStep != 0 || minute < 0 || minute > (60 - minuteStep)) {
            throw new IllegalArgumentException("Minute must be a multiple of " + minuteStep);
        }
        else if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        this.hour = hour;
        this.minute = minute;
    }

    public void setMinute(int minute) {
        if (minute % minuteStep != 0 || minute < 0 || minute > (60 - minuteStep)) {
            throw new IllegalArgumentException("Minute must be a multiple of " + minuteStep);
        }
        else if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        this.minute = minute;
    }
}
