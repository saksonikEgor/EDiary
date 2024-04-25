package com.saksonik.notificator.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        Scheduler scheduler
) {
    @Bean
    public Duration schedulerInterval() {
        return scheduler.interval();
    }

    public record Scheduler(
            boolean enable,
            @NotNull Duration interval
    ) {
    }
}
