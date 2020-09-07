package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Simulator {

    private MathUtil mathUtil = new MathUtil();
//    private final FileManager fileManager;

    private double time;
    private List<SchedulerEvent> events;
    private SimpleQueue queue;

//    @EventListener(ApplicationReadyEvent.class)
    public void run() throws InterruptedException {
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

        events = new ArrayList<>();
        SchedulerEvent event = SchedulerEvent.builder()
                .time(3)
                .type("ARRIVAL")
                .build();
        events.add(event);

        executeEvent();

    }

    private void executeEvent() throws InterruptedException {
        for (int i=1; i<=100000; i++) {
            System.out.println(queue.toString());
            SchedulerEvent event = getNextEvent();
            if(event.getType().equals("ARRIVAL")) {
                executeArrival(event);
            } else {
                executeOutput(event);
            }

            Thread.sleep(1000);
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
