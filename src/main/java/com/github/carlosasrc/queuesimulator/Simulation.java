package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulation {

    private final MathUtil mathUtil = new MathUtil();
    private double time;
    private List<SchedulerEvent> events;
    private SimpleQueue queue;

    public Simulation(SimpleQueue queue, SchedulerEvent initialEvent) {
        this.queue = queue;
        this.events = new ArrayList<>();
        events.add(initialEvent);
    }


    public void run() {
        for (int i=1; i<=100000; i++) {
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
        } else {
            queue.addLoss();
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
        SchedulerEvent event = SchedulerEvent.builder()
                .type("ARRIVAL")
                .time(mathUtil.getNextRandomTime(queue.getInitialArrive(), queue.getFinalArrive()) + time)
                .build();
        events.add(event);
    }

    private void scheduleOutput() {
        SchedulerEvent event =SchedulerEvent.builder()
                .type("OUTPUT")
                .time(mathUtil.getNextRandomTime(queue.getInitialAttendance(), queue.getFinalAttendance()) + time)
                .build();
        events.add(event);
    }

    private SchedulerEvent getNextEvent() {
        SchedulerEvent event =  events.stream()
                .min(Comparator.comparing(SchedulerEvent::getTime))
                .get();
        events.remove(event);
        return event;
    }

    private void countTime(SchedulerEvent event) {
        queue.countTime(event.getTime());
        time = event.getTime();
    }
}
