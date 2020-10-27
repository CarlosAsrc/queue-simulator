package com.github.carlosasrc.queuesimulator;

import com.github.carlosasrc.queuesimulator.io.InputManager;
import com.github.carlosasrc.queuesimulator.model.ScheduledEvent;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final ReportService reportService = new ReportService();
    private static final InputManager inputManager = new InputManager();

    public static void main(String[] args) throws CloneNotSupportedException {
        if (args.length != 1) {
            System.out.println("Número inválido de parâmetros!\nUso: java -jar queue-simulator.jar <nome-arquivo>");
            return;
        }

        List<SimpleQueue> queues;
        try {
            queues = inputManager.getInputData(args[0]);
        } catch (Exception e) {
            System.out.printf("Erro ao ler arquivo %s%n", args[0]);
            e.printStackTrace();
            return;
        }

        if (queues.size() == 1){
            simpleSimulation(queues.get(0));
        } else {
            networkSimulation(queues);
        }
    }

    private static void networkSimulation(List<SimpleQueue> queues) {
        ScheduledEvent firstEvent = ScheduledEvent.builder()
                .queueId(1)
                .time(1.0)
                .type("ARRIVAL")
                .build();

        Simulation simulation = new Simulation(queues, firstEvent);
        simulation.run();

        System.out.println(reportService.generateNetworkReport(Collections.singletonList(simulation)));
    }


    private static void simpleSimulation(SimpleQueue queue) throws CloneNotSupportedException {
        List<Simulation> simulations = new ArrayList<>();
        List<SimpleQueue> queueClonesToSimulations = Arrays.asList(
                (SimpleQueue) queue.clone(),
                (SimpleQueue) queue.clone(),
                (SimpleQueue) queue.clone(),
                (SimpleQueue) queue.clone(),
                (SimpleQueue) queue.clone());

        ScheduledEvent firstEvent = ScheduledEvent.builder()
                .time(3.0)
                .queueId(queue.getId())
                .type("ARRIVAL")
                .build();

        for(int i=0; i<5; i++) {
            queueClonesToSimulations.get(i).reset();
            Simulation simulation = new Simulation(Collections.singletonList(queueClonesToSimulations.get(i)), firstEvent);
            simulation.run();
            simulations.add(simulation);
        }

        System.out.println(reportService.generateSimpleReport(simulations));
    }
}
