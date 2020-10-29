package com.github.carlosasrc.queuesimulator.util;

import com.github.carlosasrc.queuesimulator.model.report.SimpleQueueReport;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Data
@NoArgsConstructor
public class MathUtil {

    private final long A = 25214903917L;
    private final long M = (long) Math.pow(2D, 48D);
    private final long C = 11L;
    private double seed = 1;
    private int randomGenerated = 0;

    private List<Double> aleatorios = Arrays.asList(0.2176,
            0.0103,
            0.1109,
            0.3456,
            0.9910,
            0.2323,
            0.9211,
            0.0322,
            0.1211
//            0.5131,
//            0.7208,
//            0.9172,
//            0.9922,
//            0.8324,
//            0.5011,
//            0.2931,
//            0.6621,
//            0.8574,
//            0.3964,
//            0.4042,
//            0.5058,
//            0.8261,
//            0.9902,
//            0.1230,
//            0.8496,
//            0.2949,
//            0.0839,
//            0.3417,
//            0.6933,
//            0.2636,
//            0.6777,
//            0.0605,
//            0.0371,
//            0.7324,
//            0.7714,
//            0.2792,
//            0.8808,
//            0.7011,
//            0.3652
    );


    public double getNextRandomTime(Integer A, Integer B) {
        this.randomGenerated++;
        double bla = getRandomNumber();
//        System.out.println(bla);
        return (B - A) * bla + A;
    }

    public double getRandomNumber() {
//        return aleatorios.get(randomGenerated-1);
        seed = (A * seed + C) % M;
//        System.out.println("ALEATORIO: " + seed / M);
        return seed / M;
    }


    public void getPercentages(SimpleQueueReport report) {
        for (Double state: report.getStates()) {
            report.getPercentages().add(state * 100D / report.getAverageTime());
        }
    }
}
