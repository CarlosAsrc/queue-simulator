package com.github.carlosasrc.queuesimulator;

import com.github.carlosasrc.queuesimulator.model.ScheduledEvent;
import com.github.carlosasrc.queuesimulator.model.SimpleQueue;
import com.github.carlosasrc.queuesimulator.util.MathUtil;
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
    private List<ScheduledEvent> events;
    private SimpleQueue queue;

    public Simulation(SimpleQueue queue, ScheduledEvent initialEvent) {
        this.queue = queue;
        this.events = new ArrayList<>();
        events.add(initialEvent);
    }


    public void run() {
        for (int i=1; i<=100000; i++) {
            ScheduledEvent event = getNextEvent();
            if(event.getType().equals("ARRIVAL")) {
                executeArrival(event);
            } else {
                executeOutput(event);
            }
        }
    }

    private void executeArrival(ScheduledEvent event) {
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

    private void executeOutput(ScheduledEvent event) {
        countTime(event);
        queue.decreaseCount();
        if(queue.getClientsCount() >= queue.getServers()) {
            scheduleOutput();
        }
    }

    private void scheduleArrival() {
        ScheduledEvent event = ScheduledEvent.builder()
                .type("ARRIVAL")
                .time(mathUtil.getNextRandomTime(queue.getMinArrivalFrequency(), queue.getMaxArrivalFrequency()) + time)
                .build();
        events.add(event);
    }

    private void scheduleOutput() {
        ScheduledEvent event = ScheduledEvent.builder()
                .type("OUTPUT")
                .time(mathUtil.getNextRandomTime(queue.getMinOutputFrequency(), queue.getMaxOutputFrequency()) + time)
                .build();
        events.add(event);
    }

    private ScheduledEvent getNextEvent() {
        ScheduledEvent event =  events.stream()
                .min(Comparator.comparing(ScheduledEvent::getTime))
                .get();
        events.remove(event);
        return event;
    }

    private void countTime(ScheduledEvent event) {
        queue.countTime(event.getTime() - time);
        time = event.getTime();
    }
}
