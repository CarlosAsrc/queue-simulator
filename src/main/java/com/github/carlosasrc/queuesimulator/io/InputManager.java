package com.github.carlosasrc.queuesimulator.io;

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
}
