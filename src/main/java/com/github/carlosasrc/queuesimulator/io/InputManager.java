package com.github.carlosasrc.queuesimulator.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputManager {

    public List<SimpleQueue> getInputData(String fileName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        List<SimpleQueue> queues = om.readValue(file, new TypeReference<List<SimpleQueue>>(){});

        queues.forEach(simpleQueue -> {
                simpleQueue.setStates(new ArrayList<>(Collections.nCopies(simpleQueue.getCapacity() + 1, 0d)));
                if (simpleQueue.getRoutes() == null) simpleQueue.setRoutes(new ArrayList<>());
        });

        queues.forEach(System.out::println);
        return queues;
    }
}
