package com.github.carlosasrc.queuesimulator;

import com.github.carlosasrc.queuesimulator.io.InputManager;
import com.github.carlosasrc.queuesimulator.model.ScheduledEvent;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        if (args.length != 1) {
            System.out.println("Número inválido de parâmetros!\nUso: java -jar queue-simulator.jar <nome-arquivo>");
            return;
        }
        ReportService reportService = new ReportService();
        InputManager inputManager = new InputManager();
        List<SimpleQueue> queues;
        try {
            queues = inputManager.getInputData(args[0]);
        } catch (Exception e) {
            System.out.printf("Arquivo %s não encontrado na raíz do projeto!%n", args[0]);
            return;
        }


        for (SimpleQueue queue: queues) {
            List<Simulation> simulations = new ArrayList<>();
            List<SimpleQueue> queueClonesToSimulations = Arrays.asList(
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone(),
                    (SimpleQueue) queue.clone());

            ScheduledEvent firstEvent = ScheduledEvent.builder()
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
