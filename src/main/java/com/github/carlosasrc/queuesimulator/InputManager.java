package com.github.carlosasrc.queuesimulator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
public class InputManager {
    public SimpleQueue readQueueData() {
        return SimpleQueue.builder()
                .clientsCount(0)
                .servers(1)
                .capacity(5)
                .initialArrive(2)
                .finalArrive(4)
                .initialAttendance(3)
                .finalAttendance(5)
                .states(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d))
                .build();
    }

}
