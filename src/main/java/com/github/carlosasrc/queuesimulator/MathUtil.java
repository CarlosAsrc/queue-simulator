package com.github.carlosasrc.queuesimulator;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class MathUtil {

    private final long A = 25214903917L;
    private final long M = (long) Math.pow(2D, 48D);
    private final long C = 11L;
    private Queue<Double> randomNumbers;

    public MathUtil() {
        randomNumbers = getRandomNumbers();
    }

    public double getNextRandomTime(Integer initialAttendance, Integer finalAttendance) {
        return finalAttendance - initialAttendance * randomNumbers.poll() +initialAttendance;
    }

    public Queue<Double> getRandomNumbers() {
        double seed = System.currentTimeMillis();
        Queue<Double> randomNumbers = new LinkedList<>();
        for(int i=1; i<=M; i++) {
            double randomNumber = (A * seed + C) % M;
            seed = randomNumber;
            randomNumbers.add((randomNumber / M));
        }
        return randomNumbers;
    }

}
