package com.github.carlosasrc.queuesimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        ReportService reportService = new ReportService();
        InputManager inputManager = new InputManager();

        List<SimpleQueue> queues = inputManager.getInputData();

        for (SimpleQueue queue: queues) {
            List<Simulation> simulations = new ArrayList<>();
            List<SimpleQueue> queueClonesToSimulations = Arrays.asList(
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone());

            SchedulerEvent firstEvent = SchedulerEvent.builder()
                    .time(3)
                    .type("ARRIVAL")
                    .build();

            for(int i=0; i<5; i++) {
                queueClonesToSimulations.get(i).reset();
                Simulation simulation = new Simulation(queueClonesToSimulations.get(i), firstEvent);
                simulation.run();
                simulations.add(simulation);
            }


            System.out.println(reportService.generateReport(simulations));
        }
    }
}
