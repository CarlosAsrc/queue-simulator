package com.github.carlosasrc.queuesimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        ReportService reportService = new ReportService();
        InputManager inputManager = new InputManager();
        List<Simulation> simulations = new ArrayList<>();

        List<SimpleQueue> queues = Arrays.asList(
                (SimpleQueue) inputManager.readQueueData().clone(),
                (SimpleQueue) inputManager.readQueueData().clone(),
                (SimpleQueue) inputManager.readQueueData().clone(),
                (SimpleQueue) inputManager.readQueueData().clone(),
                (SimpleQueue) inputManager.readQueueData().clone());

        SchedulerEvent firstEvent = SchedulerEvent.builder()
                .time(3)
                .type("ARRIVAL")
                .build();

        for(int i=0; i<5; i++) {
            queues.get(i).reset();
            Simulation simulation = new Simulation(queues.get(i), firstEvent);
            simulation.run();
            simulations.add(simulation);
        }


        System.out.println(reportService.generateReport(simulations.get(0)));
    }
}
