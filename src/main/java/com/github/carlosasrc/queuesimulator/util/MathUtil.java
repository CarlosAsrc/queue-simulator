package com.github.carlosasrc.queuesimulator.util;

import com.github.carlosasrc.queuesimulator.model.report.SimpleQueueReport;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MathUtil {

    private final long A = 25214903917L;
    private final long M = (long) Math.pow(2D, 48D);
    private final long C = 11L;
    private double seed = 1;
    private int randomGenerated = 0;


    public double getNextRandomTime(Integer A, Integer B) {
        this.randomGenerated++;
        double bla = getRandomNumber();
        return (B - A) * bla + A;
    }

    public double getRandomNumber() {
        seed = (A * seed + C) % M;
        return seed / M;
    }


    public void getPercentages(SimpleQueueReport report) {
        for (Double state: report.getStates()) {
            report.getPercentages().add(state * 100D / report.getAverageTime());
        }
    }
}
