package com.github.carlosasrc.queuesimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScheduledEvent {
    private String type;
    private double time;
}
