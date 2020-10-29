package com.github.carlosasrc.queuesimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleQueue implements Cloneable {
    private int id;
    private int clientsCount;
    private int servers;
    private Integer capacity;
    private List<Double> states;
    private List<Route> routes;
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
        if (Objects.isNull(states)) {
            states = new ArrayList<>();
        }
        if(states.size() == 0 || states.size() <= clientsCount) {
            for(int i=states.size(); i <= clientsCount; i++) {
                states.add(0d);
            }
        }
        states.set(clientsCount, states.get(clientsCount) + eventTime);
    }

    public void addLoss() {
        losses++;
    }

    public void reset() {
        clientsCount = 0;
        losses = 0;
        states = new ArrayList<>(Collections.nCopies(capacity + 1, 0d));
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Optional<Integer> getMoreLikelyRoutingQueue(double probability) {
        for (Route route: routes) {
            if (probability > route.getBegin() && probability <= route.getEnd()) return Optional.of(route.getId());
        }
        return Optional.empty();
    }
}
