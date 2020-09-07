package com.github.carlosasrc.queuesimulator.io;

import com.github.carlosasrc.queuesimulator.model.SimpleQueue;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InputManager {
    public ArrayList<SimpleQueue> getInputData() throws IOException {
        ArrayList<SimpleQueue> queueList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("example.txt"));

        String line = reader.readLine();

        int id = 0;

        while (line != null) {
            String[] regex = line.split(" ");

            SimpleQueue simpleQueue = SimpleQueue.builder()
                    .id(id)
                    .clientsCount(0)
                    .servers(Integer.parseInt(regex[0]))
                    .capacity(Integer.parseInt(regex[1]))
                    .minArrivalFrequency(Integer.parseInt(regex[2]))
                    .maxArrivalFrequency(Integer.parseInt(regex[3]))
                    .minOutputFrequency(Integer.parseInt(regex[4]))
                    .maxOutputFrequency(Integer.parseInt(regex[5]))
                    .states(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d))
                    .build();

            queueList.add(simpleQueue);

            id++;
            line = reader.readLine();
        }
        reader.close();
        return queueList;
    }
}
