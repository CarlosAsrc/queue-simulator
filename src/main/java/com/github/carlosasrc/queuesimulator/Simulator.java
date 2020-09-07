package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Simulator {

    private MathUtil mathUtil = new MathUtil();
//    private final FileManager fileManager;

    private Double time;
    private Double lastArrivalTime;
    private Double lastOutputTime;
    private List<SchedulerEvent> events;
    private SimpleQueue queue;

//    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        queue = SimpleQueue.builder()
                .clientsCount(0)
                .servers(1)
                .capacity(5)
                .initialArrive(2)
                .finalArrive(4)
                .states(new Double[]{0d, 0d, 0d, 0d, 0d, 0d})
                .initialAttendance(3)
                .finalAttendance(5)
                .build();

        events = Collections.singletonList(SchedulerEvent.builder()
                .time(3)
                .type("ARRIVAL")
                .build());

        executeEvent();

    }

    private void executeEvent() {
        while (!events.isEmpty()) {
            System.out.println(queue.toString());
            SchedulerEvent event = getNextEvent();
            if(event.getType().equals("ARRIVAL")) {
                executeArrival(event);
            } else {
                executeOutput(event);
            }
        }
    }

    private void executeArrival(SchedulerEvent event) {
        countTime(event);
        if(queue.getClientsCount() < queue.getCapacity()) {
            queue.increaseCount();
            if(queue.getClientsCount() <= queue.getServers()) {
                scheduleOutput();
            }
        }
        scheduleArrival();
    }

    private void executeOutput(SchedulerEvent event) {
        countTime(event);
        queue.decreaseCount();
        if(queue.getClientsCount() >= queue.getServers()) {
            scheduleOutput();
        }
    }

    private void scheduleArrival() {
        events.add(SchedulerEvent.builder()
                .time(mathUtil.getNextRandomTime(queue.getInitialArrive(), queue.getFinalArrive()) + lastArrivalTime)
                .build()
        );
    }

    private void scheduleOutput() {
        events.add(SchedulerEvent.builder()
                .time(mathUtil.getNextRandomTime(queue.getInitialAttendance(), queue.getFinalAttendance()) + lastOutputTime)
                .build()
        );
    }

    private SchedulerEvent getNextEvent() {
        return events.stream()
                .min(Comparator.comparing(SchedulerEvent::getTime))
                .get();
    }

    private void countTime(SchedulerEvent event) {
        queue.countTime(event.getTime());
        time = event.getTime();
        if(event.getType().equals("ARRIVAL")) {
            lastArrivalTime = event.getTime();
        } else {
            lastOutputTime = event.getTime();
        }
    }
}
