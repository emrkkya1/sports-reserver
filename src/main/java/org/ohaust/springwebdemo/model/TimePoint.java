package org.ohaust.springwebdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimePoint {

    private int hour;
    private int minute;

    public TimePoint getPrev() {
        if (minute < 15) {
            return new TimePoint(hour - 1, minute + 45);
        }
        return new TimePoint(hour, minute - 15);
    }

    public TimePoint getNext() {
        if (minute > 45) {
            return new TimePoint(hour + 1, minute - 45);
        }
        return new TimePoint(hour, minute + 15);
    }
}
