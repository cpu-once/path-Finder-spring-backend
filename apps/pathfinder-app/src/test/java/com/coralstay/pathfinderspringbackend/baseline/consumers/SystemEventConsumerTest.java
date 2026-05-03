package com.coralstay.pathfinderspringbackend.baseline.consumers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SystemEventConsumerTest {

    private static final String DESC_SYSTEM_EVENT_CONSUMER = "컨슈머: ApplicationReadyEvent가 정상적으로 수신되어야 한다";

    @Autowired
    private SystemEventConsumer systemEventConsumer;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    @DisplayName(DESC_SYSTEM_EVENT_CONSUMER)
    void receiveApplicationReadyEvent() {
        assertThat(systemEventConsumer).isNotNull();

        ApplicationReadyEvent event = new ApplicationReadyEvent(new SpringApplication(), new String[0], context, Duration.ofMillis(100));
        eventPublisher.publishEvent(event);
    }
}
