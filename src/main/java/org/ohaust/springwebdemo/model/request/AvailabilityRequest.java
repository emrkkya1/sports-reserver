package org.ohaust.springwebdemo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.TimeInterval;

import java.util.List;

@Data
@NoArgsConstructor
public class AvailabilityRequest {

    private DateInfo dateInfo;
    private List<TimeInterval> timeIntervals;

}
