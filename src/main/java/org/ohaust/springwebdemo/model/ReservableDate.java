package org.ohaust.springwebdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ReservableDate {

    @Id
    String id;

    private final Date date;
    private final List<ReservableQuarterHour> reservableQuarterHourList;

}
