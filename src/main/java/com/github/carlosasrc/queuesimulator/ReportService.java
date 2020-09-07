package com.github.carlosasrc.queuesimulator;

import java.util.Collections;
import java.util.List;

public class ReportService {

    private final MathUtil mathUtil = new MathUtil();

    public String generateReport(Simulation simulation) {
        SimpleQueue simpleQueue = simulation.getQueue();
        List<Double> percentages = mathUtil.getPercentages(simpleQueue);
        return
               "\nClientes na fila: " + simpleQueue.getClientsCount()+
               "\nServidores: " + simpleQueue.getServers() +
               "\nCapacidade: " + simpleQueue.getCapacity() +
               String.format("\n\nTempo Total: %.4f", mathUtil.getTotalTime(simpleQueue))+
               getStatesReport(simpleQueue.getStates(), percentages)+
               "\nPERDAS=" + simpleQueue.getLosses();
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
