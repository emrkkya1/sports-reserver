package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservableQuarterHour {

    private TimePoint startTime;
    private boolean isReserved = false;

}
