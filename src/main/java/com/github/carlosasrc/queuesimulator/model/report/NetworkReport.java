package com.github.carlosasrc.queuesimulator.model.report;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NetworkReport {
    private double averageTime;
    private List<SimpleQueueReport> queueReports;
}
