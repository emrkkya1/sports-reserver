package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailabilityRequest {

    DateModel dateModel;
    TimePointModel timeFrom;
    TimePointModel timeTo;

}
