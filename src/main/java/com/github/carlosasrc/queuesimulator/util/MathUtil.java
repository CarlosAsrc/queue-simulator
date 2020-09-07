package com.github.carlosasrc.queuesimulator.util;

import com.github.carlosasrc.queuesimulator.model.FinalReport;
import lombok.NoArgsConstructor;


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


    public void getPercentages(FinalReport report) {
        double totalTime = getTotalTime(report);
        for (Double state: report.getStates()) {
            report.getPercentages().add(state * 100D / totalTime);
        }
    }

    public double getTotalTime(FinalReport report) {
        return report
                .getStates()
                .stream()
                .reduce(Double::sum)
                .orElse(0D);
    }
}
