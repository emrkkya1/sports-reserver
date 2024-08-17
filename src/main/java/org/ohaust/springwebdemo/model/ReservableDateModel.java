package org.ohaust.springwebdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ReservableDateModel {

    @Id
    String id;

    private final DateModel dateModel;
    private List<ReservableTimeIntervalModel> reservableQuarterHourList;

}
