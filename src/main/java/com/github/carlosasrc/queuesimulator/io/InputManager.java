package com.github.carlosasrc.queuesimulator.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.carlosasrc.queuesimulator.model.Route;
import com.github.carlosasrc.queuesimulator.model.ScheduledEvent;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InputManager {

    public List<SimpleQueue> getInputData(String fileName) throws IOException {
        File file = new File(fileName);

        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        List<SimpleQueue> queues = om.readValue(file, new TypeReference<List<SimpleQueue>>(){});

        queues.forEach(simpleQueue -> {
                simpleQueue.setCapacity(Objects.isNull(simpleQueue.getCapacity()) ? 1000 : simpleQueue.getCapacity());
                if (simpleQueue.getRoutes() == null) {
                    simpleQueue.setRoutes(new ArrayList<>());
                } else {
                    List<Route> routes =  simpleQueue.getRoutes().stream()
                            .sorted(Comparator.comparing(Route::getProbability))
                            .collect(Collectors.toList());
                    simpleQueue.setRoutes(routes);
                }
        });

        return queues;
    }
}
