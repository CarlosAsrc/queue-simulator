package com.github.carlosasrc.queuesimulator.io;

import com.github.carlosasrc.queuesimulator.model.Route;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class InputManager {
    public ArrayList<SimpleQueue> getInputData(String fileName) throws IOException {
        ArrayList<SimpleQueue> queueList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line = reader.readLine();

        int id = 0;

        while (line != null) {
            String[] regex = line.split(" ");

            int capacity = Integer.parseInt(regex[1]);

            SimpleQueue simpleQueue = SimpleQueue.builder()
                    .id(id)
                    .clientsCount(0)
                    .routes(new ArrayList<>())
                    .servers(Integer.parseInt(regex[0]))
                    .capacity(capacity)
                    .minArrivalFrequency(Integer.parseInt(regex[2]))
                    .maxArrivalFrequency(Integer.parseInt(regex[3]))
                    .minOutputFrequency(Integer.parseInt(regex[4]))
                    .maxOutputFrequency(Integer.parseInt(regex[5]))
                    .states(new ArrayList<>(Collections.nCopies(capacity + 1, 0d)))
                    .build();

            queueList.add(simpleQueue);

            id++;
            line = reader.readLine();
        }
        reader.close();
        return queueList;
    }


    public ArrayList<SimpleQueue> getMock() {
        ArrayList<SimpleQueue> queueList = new ArrayList<>();

        SimpleQueue queue1 = SimpleQueue.builder()
                .id(1)
                .clientsCount(0)
                .routes(Collections.singletonList(Route.builder().id(2).probability(1).build()))
                .servers(2)
                .capacity(3)
                .minArrivalFrequency(2)
                .maxArrivalFrequency(3)
                .minOutputFrequency(2)
                .maxOutputFrequency(5)
                .states(new ArrayList<>(Collections.nCopies(3 + 1, 0d)))
                .build();

        SimpleQueue queue2 = SimpleQueue.builder()
                .id(2)
                .clientsCount(0)
                .routes(new ArrayList<>())
                .servers(1)
                .capacity(3)
                .minArrivalFrequency(0)
                .maxArrivalFrequency(0)
                .minOutputFrequency(3)
                .maxOutputFrequency(5)
                .states(new ArrayList<>(Collections.nCopies(3 + 1, 0d)))
                .build();

        queueList.add(queue1);
        queueList.add(queue2);

        return queueList;
    }
}
