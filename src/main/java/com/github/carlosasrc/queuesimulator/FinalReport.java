package com.github.carlosasrc.queuesimulator;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FinalReport {
    private int queueId;
    private double averageTime;
    private int losses;
    private int servers;
    private int capacity;
    private List<Double> states;
    private List<Double> percentages;
}
