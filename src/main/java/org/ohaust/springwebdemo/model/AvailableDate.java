package org.ohaust.springwebdemo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
public class AvailableDate {
    @Field("date")
    private LocalDate date;

    @Field("available_hours")
    private List<AvailableHour> availableHours;
}
