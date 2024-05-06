package com.saksonik.configuration;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.client.NotificatorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public NotificatorClient notificatorClient(@Value("${app.base-url.notificator}") String notificatorBaseUrl) {
        return new NotificatorClient(WebClient.builder()
                .baseUrl(notificatorBaseUrl)
                .build());
    }

    @Bean
    public HeadManagerClient headManagerClient(@Value("${app.base-url.head-manager}") String headManagerBaseUrl) {
        return new HeadManagerClient(WebClient.builder()
                .baseUrl(headManagerBaseUrl)
                .build());
    }
}
