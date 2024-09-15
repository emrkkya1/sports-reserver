package org.ohaust.springwebdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateInfo {
    private int year;
    private int month;
    private int day;
}
