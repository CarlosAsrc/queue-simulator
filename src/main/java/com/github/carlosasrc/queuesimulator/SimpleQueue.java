package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class SimpleQueue {
    private Integer clientsCount;
    private Integer servers;
    private Integer capacity;
    private Double[] states;
    private Integer initialArrive;
    private Integer finalArrive;
    private Integer initialAttendance;
    private Integer finalAttendance;

    public void increaseCount() {
        clientsCount = clientsCount++;
    }

    public void decreaseCount() {
        clientsCount = clientsCount--;
    }

    public void countTime(double eventTime) {
        states[clientsCount] = states[clientsCount] + eventTime;
    }

}
