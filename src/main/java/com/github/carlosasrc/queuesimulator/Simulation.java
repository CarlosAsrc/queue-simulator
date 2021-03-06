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
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulation implements Cloneable {

    private final MathUtil mathUtil = new MathUtil();
    private double time;
    private List<ScheduledEvent> events;
    private List<SimpleQueue> queues;

    public Simulation(List<SimpleQueue> queues, ScheduledEvent initialEvent) {
        this.queues = queues;
        this.events = new ArrayList<>();
        events.add(initialEvent);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public void run() {
        while (mathUtil.getRandomGenerated() <= 100000) {
            ScheduledEvent event = getNextEvent();
            if(event.getType().equals("ARRIVAL")) {
                executeArrival(event);
            } else {
                executeOutput(event);
            }
        }
    }

    private void executeArrival(ScheduledEvent event) {
        SimpleQueue queue = getQueueById(event.getQueueId());

        countTime(event);
        if(queue.getClientsCount() < queue.getCapacity()) {
            queue.increaseCount();
            if(queue.getClientsCount() <= queue.getServers()) {
                scheduleOutput(queue.getId());
            }
        } else {
            queue.addLoss();
        }
        scheduleArrival(queue.getId());
    }

    private void executeOutput(ScheduledEvent event) {
        SimpleQueue originQueue = getQueueById(event.getQueueId());

        countTime(event);
        originQueue.decreaseCount();
        if(originQueue.getClientsCount() >= originQueue.getServers()) {
            scheduleOutput(originQueue.getId());
        }


        //SE PUSSUI FILA DE ROTEAMENTO:
        Optional<SimpleQueue> destinyQueueOptional = getRoutingQueue(originQueue);
        if(destinyQueueOptional.isPresent()) {
            SimpleQueue destinyQueue = destinyQueueOptional.get();
            if(destinyQueue.getClientsCount() < destinyQueue.getCapacity()) {
                destinyQueue.increaseCount();
                if(destinyQueue.getClientsCount() <= destinyQueue.getServers()) {
                    scheduleOutput(destinyQueue.getId());
                }
            } else {
                destinyQueue.addLoss();
            }
        }
    }


    private void scheduleArrival(int queueId) {
        SimpleQueue queue = getQueueById(queueId);
        ScheduledEvent event = ScheduledEvent.builder()
                .type("ARRIVAL")
                .queueId(queueId)
                .time(mathUtil.getNextRandomTime(queue.getMinArrivalFrequency(), queue.getMaxArrivalFrequency()) + time)
                .build();
        events.add(event);
    }


    private void scheduleOutput(int queueId) {
        SimpleQueue queue = getQueueById(queueId);
        ScheduledEvent event = ScheduledEvent.builder()
                .type("OUTPUT")
                .queueId(queueId)
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
        queues.forEach(queue -> queue.countTime(event.getTime() - time));
        time = event.getTime();
    }


    private SimpleQueue getQueueById(int queueId) {
        return queues.stream()
                .filter(queue -> queue.getId() == queueId)
                .findFirst()
                .get();
    }

    private Optional<SimpleQueue> getRoutingQueue(SimpleQueue originQueue) {
        if (originQueue.getRoutes().size() == 0)
            return Optional.empty();

        Optional<Integer> routingQueueId;

        if(originQueue.getRoutes().size() == 1 && originQueue.getRoutes().get(0).getProbability() == 1.0) {
            routingQueueId = originQueue.getMoreLikelyRoutingQueue(1.0);
        } else {
            routingQueueId = originQueue.getMoreLikelyRoutingQueue(mathUtil.getNextRandomTime(0, 1));
        }
        return routingQueueId.map(this::getQueueById);
    }
}
