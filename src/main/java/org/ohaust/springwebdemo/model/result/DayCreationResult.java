package org.ohaust.springwebdemo.model.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DayCreationResult {
    private final boolean success;
    private final String message;
}
