package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.TimeModel;

@Data
@NoArgsConstructor
public class ReservationRequest {

    private DateInfo dateInfo;
    private TimeModel reservationStart;
    private TimeModel reservationEnd;

}
