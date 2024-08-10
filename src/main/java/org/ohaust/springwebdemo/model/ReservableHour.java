package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ReservableHour {

    private int hour;
    private boolean isReserved = false;

}
