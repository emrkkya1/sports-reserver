package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationRequest {

    private DateModel dateModel;
    private TimePointModel timeFrom;
    private TimePointModel timeTo;

}
