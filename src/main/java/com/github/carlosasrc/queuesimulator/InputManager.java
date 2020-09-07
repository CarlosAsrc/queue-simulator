package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class InputManager {
    public ArrayList<SimpleQueue> getInputData() {
        SimpleQueue mock1 = SimpleQueue.builder()
                .id(1)
                .clientsCount(0)
                .servers(1)
                .capacity(5)
                .minArrivalFrequency(2)
                .maxArrivalFrequency(4)
                .minOutputFrequency(3)
                .maxOutputFrequency(5)
                .states(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d))
                .build();

        SimpleQueue mock2 = SimpleQueue.builder()
                .id(1)
                .clientsCount(0)
                .servers(2)
                .capacity(5)
                .minArrivalFrequency(2)
                .maxArrivalFrequency(4)
                .minOutputFrequency(3)
                .maxOutputFrequency(5)
                .states(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d))
                .build();

        ArrayList<SimpleQueue> queueList = new ArrayList<>();
        queueList.add(mock1);
        queueList.add(mock2);
        return queueList;
    }

}
