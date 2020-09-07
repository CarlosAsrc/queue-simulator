package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SimpleQueue implements Cloneable{
    private int id;
    private int clientsCount;
    private int servers;
    private Integer capacity;
    private List<Double> states;
    private int minArrivalFrequency;
    private int maxArrivalFrequency;
    private int minOutputFrequency;
    private int maxOutputFrequency;
    private int losses;

    public void increaseCount() {
        clientsCount++;
    }

    public void decreaseCount() {
        clientsCount--;
    }

    public void countTime(double eventTime) {
        states.set(clientsCount, states.get(clientsCount) + eventTime);
    }

    public void addLoss() {
        losses++;
    }

    public void reset() {
        clientsCount = 0;
        losses = 0;
        for (Double state: states) {
            state = 0D;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
