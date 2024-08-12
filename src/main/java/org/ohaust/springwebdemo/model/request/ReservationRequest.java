package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ohaust.springwebdemo.model.Date;

@Data
@NoArgsConstructor
public class ReservationRequest {

    private Date date;
    private int hour;

}
