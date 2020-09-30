package com.github.carlosasrc.queuesimulator;

import com.github.carlosasrc.queuesimulator.model.report.SimpleQueueReport;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;
import com.github.carlosasrc.queuesimulator.model.report.TandemReport;
import com.github.carlosasrc.queuesimulator.util.MathUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private final MathUtil mathUtil = new MathUtil();

    public String generateSimpleReport(List<Simulation> simulations) {
        SimpleQueueReport simpleQueueReport = integrateSimpleSimulations(simulations);
        mathUtil.getPercentages(simpleQueueReport);
        return
               String.format("\nRESULTADO DA MÉDIA DE 5 EXECUÇÕES PARA A FILA %s:", simpleQueueReport.getQueueId()) +
               "\nServidores: " + simpleQueueReport.getServers() +
               "\nCapacidade: " + simpleQueueReport.getCapacity() +
               String.format("\nTempo Médio Total: %.4f", simpleQueueReport.getAverageTime())+
               getStatesReport(simpleQueueReport.getStates(), simpleQueueReport.getPercentages())+
               "\nPERDAS=" + simpleQueueReport.getLosses();
    }

    private SimpleQueueReport integrateSimpleSimulations(List<Simulation> simulations) {
        double averageTime = (simulations.stream().mapToDouble(Simulation::getTime).sum()) / 5.0;
        List<Double> averageStates = new ArrayList<>();

        List<SimpleQueue> queues = simulations.stream()
                .map(Simulation::getQueues)
                .map(simpleQueues -> simpleQueues.get(0))
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

        return SimpleQueueReport.builder()
                .queueId(queueId)
                .averageTime(averageTime)
                .losses(averageLoss)
                .states(averageStates)
                .capacity(capacity)
                .servers(servers)
                .percentages(new ArrayList<>())
                .build();
    }

    private String getStatesReport(List<Double> times, List<Double> percentages) {
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

    private String getFiller(int size) {
        return String.join("", Collections.nCopies(60 - size, " "));
    }



    public String generateTandemReport(List<Simulation> simulations) {
        StringBuilder stringBuilder = new StringBuilder();
        TandemReport tandemReport = integrateTandemSimulations(simulations);

        stringBuilder.append("\n Tempo total de simulação: ").append(tandemReport.getAverageTime());

        for (SimpleQueueReport simpleQueueReport: tandemReport.getQueueReports()) {
            stringBuilder.append(simpleQueueReport.getStringReport());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private TandemReport integrateTandemSimulations(List<Simulation> simulations) {
        Simulation simulation = simulations.get(0);
        List<SimpleQueueReport> queuesReports = new ArrayList<>();

        for (SimpleQueue simpleQueue: simulation.getQueues()) {
            SimpleQueueReport simpleQueueReport =  SimpleQueueReport.builder()
                    .queueId(simpleQueue.getId())
                    .averageTime(simulation.getTime())
                    .losses(simpleQueue.getLosses())
                    .states(simpleQueue.getStates())
                    .capacity(simpleQueue.getCapacity())
                    .servers(simpleQueue.getServers())
                    .percentages(new ArrayList<>())
                    .build();
            mathUtil.getPercentages(simpleQueueReport);
            queuesReports.add(simpleQueueReport);
        }

        return TandemReport.builder()
                .averageTime(simulation.getTime())
                .queueReports(queuesReports)
                .build();
    }
}
