package com.github.carlosasrc.queuesimulator;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class MathUtil {

    private final long A = 25214903917L;
    private final long M = (long) Math.pow(2D, 48D);
    private final long C = 11L;

    private double seed = System.currentTimeMillis();


    public double getNextRandomTime(Integer A, Integer B) {
        return (B - A) * getRandomNumber() + A;
    }

    public double getRandomNumber() {
        seed = (A * seed + C) % M;
        return seed / M;
    }


    public List<Double> getPercentages(SimpleQueue simpleQueue) {
        List<Double> percentages = new ArrayList<>();
        double totalTime = getTotalTime(simpleQueue);
        for (Double state: simpleQueue.getStates()) {
            percentages.add(state * 100D / totalTime);
        }
        return percentages;
    }

    public double getTotalTime(SimpleQueue simpleQueue) {
        return simpleQueue
                .getStates()
                .stream()
                .reduce(Double::sum)
                .orElse(0D);
    }
}
