package com.saksonik.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class NotificatorClient {
    private final WebClient webClient;
}
