package com.github.carlosasrc.queuesimulator.model.report;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class SimpleQueueReport {
    private int queueId;
    private double averageTime;
    private int losses;
    private int servers;
    private int capacity;
    private List<Double> states;
    private List<Double> percentages;


    public String getStringReport() {
        String report = "";
        String line = "";

        report = report.concat("\nFILA: "+queueId);
        report = report.concat("  G/G/"+servers+(capacity==1000?"":"/"+capacity));
        for (int i=0; i<states.size(); i++) {
            line = String.format("\n%s clientes na fila -->  TEMPO: %.4f", i, states.get(i));
            report = report.concat(line);
            report = report.concat(getFiller(line.length()));
            report = report.concat(String.format("PORCENTAGEM: %.11f", percentages.get(i))+" %");
        }
        report = report.concat("\nPerdas: "+losses);

        return report;
    }

    private String getFiller(int size) {
        return String.join("", Collections.nCopies(60 - size, " "));
    }
}
