package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SchedulerEvent {
    private String type;
    private double time;
}
