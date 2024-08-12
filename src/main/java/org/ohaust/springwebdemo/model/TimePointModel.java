package org.ohaust.springwebdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimePointModel {

    private int hour;
    private int minute;

}
