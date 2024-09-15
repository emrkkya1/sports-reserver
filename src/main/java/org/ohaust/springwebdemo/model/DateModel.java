package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
public class DateModel {
    @MongoId
    private String id;
    private DateInfo dateInfo;
    private List<TimeInterval> timeIntervals;

    public DateModel(int year, int month, int day, TimeInterval... initialInterval) {
        dateInfo = new DateInfo(year, month, day);
        this.timeIntervals = List.of(initialInterval);
    }

    public DateModel(DateInfo dateInfo, List<TimeInterval> timeIntervals) {
        this.dateInfo = dateInfo;
        this.timeIntervals = timeIntervals;
    }

}
