package com.github.carlosasrc.queuesimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private final MathUtil mathUtil = new MathUtil();

    public String generateReport(List<Simulation> simulations) {
        FinalReport finalReport = integrateSimulations(simulations);
        mathUtil.getPercentages(finalReport);
        return
               String.format("\nRESULTADO DA MÉDIA DE 5 EXECUÇÕES PARA A FILA %s:", finalReport.getQueueId()) +
               "\nServidores: " + finalReport.getServers() +
               "\nCapacidade: " + finalReport.getCapacity() +
               String.format("\nTempo Médio Total: %.4f", mathUtil.getTotalTime(finalReport))+
               getStatesReport(finalReport.getStates(), finalReport.getPercentages())+
               "\nPERDAS=" + finalReport.getLosses();
    }

    private FinalReport integrateSimulations(List<Simulation> simulations) {
        double averageTime = (simulations.stream().mapToDouble(Simulation::getTime).sum()) / 5.0;
        List<Double> averageStates = new ArrayList<>();

        List<SimpleQueue> queues = simulations.stream()
                .map(Simulation::getQueue)
                .collect(Collectors.toList());

        int capacity = queues.get(0).getCapacity();
        int servers = queues.get(0).getServers();
        int queueId = queues.get(0).getServers();
        for (int i=0; i<=capacity; i++) {
            int finalI = i;
            Double averageState = queues.stream()
                    .mapToDouble(simpleQueue -> simpleQueue.getStates().get(finalI))
                    .reduce(Double::sum)
                    .orElse(0D) / capacity;
            averageStates.add(averageState);
        }
        int averageLoss = (queues.stream().mapToInt(SimpleQueue::getLosses).sum()) / 5;

        return FinalReport.builder()
                .queueId(queueId)
                .averageTime(averageTime)
                .losses(averageLoss)
                .states(averageStates)
                .capacity(capacity)
                .servers(servers)
                .percentages(new ArrayList<>())
                .build();
    }

    public String getStatesReport(List<Double> times, List<Double> percentages) {
        String report = "";
        String line = "";
        for (int i=0; i<times.size(); i++) {
            line = String.format("\n%s clientes na fila -->  TEMPO: %.4f", i, times.get(i));
            report = report.concat(line);
            report = report.concat(getFiller(line.length()));
            report = report.concat(String.format("PORCENTAGEM: %.11f", percentages.get(i))+" %");
        }
        return report;
    }

    public String getFiller(int size) {
        return String.join("", Collections.nCopies(60 - size, " "));
    }
}
