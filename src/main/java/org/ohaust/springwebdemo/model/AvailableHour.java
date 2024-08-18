package org.ohaust.springwebdemo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalTime;

@Data
public class AvailableHour {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isReserved;
    @Indexed // not so sure? may not be an index
    private String reservationId;

}
