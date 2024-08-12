package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.TimePointModel;

@Data
@NoArgsConstructor
public class ReservationRequest {

    private DateModel dateModel;
    private TimePointModel timeFrom;
    private TimePointModel timeTo;

}
