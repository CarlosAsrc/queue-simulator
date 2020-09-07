package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SimpleQueue {
    private int clientsCount;
    private int servers;
    private Integer capacity;
    private Double[] states;
    private int initialArrive;
    private int finalArrive;
    private int initialAttendance;
    private int finalAttendance;
    private int losses;

    public void increaseCount() {
        clientsCount++;
    }

    public void decreaseCount() {
        clientsCount--;
    }

    public void countTime(double eventTime) {
        states[clientsCount] = states[clientsCount] + eventTime;
    }

    public void addLoss() {
        losses++;
    }
}
