package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservableQuarterHourModel {

    private TimePointModel startTime;
    private boolean isReserved = false;

}
