package com.github.carlosasrc.queuesimulator;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MathUtil {

    private final long A = 25214903917L;
    private final long M = (long) Math.pow(2D, 48D);
    private final long C = 11L;
    private double seed = System.currentTimeMillis();


    public double getNextRandomTime(Integer A, Integer B) {
        return B - A * getRandomNumber() + A;
    }

    public Double getRandomNumber() {
        seed = (A * seed + C) % M;
        return seed / M;
    }

}
