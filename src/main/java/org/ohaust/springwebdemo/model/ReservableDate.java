package org.ohaust.springwebdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservableDate {

    @Id
    private String id;

    private Date date;
    private List<ReservableHour> reservableHourList;

}
