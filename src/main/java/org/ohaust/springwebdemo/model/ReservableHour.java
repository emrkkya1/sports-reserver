package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservableHour {

    private int hour;
    private boolean isReserved = false;

}
