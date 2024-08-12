package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ohaust.springwebdemo.model.Date;
import org.ohaust.springwebdemo.model.TimePoint;

@Data
@NoArgsConstructor
public class AvailabilityRequest {

    Date date;
    TimePoint timeFrom;
    TimePoint timeTo;

}
