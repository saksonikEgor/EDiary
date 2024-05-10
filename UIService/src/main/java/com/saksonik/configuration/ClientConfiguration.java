package com.saksonik.configuration;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.client.NotificatorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    @Scope("prototype")
    public WebClient.Builder servicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
                        authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");

        return WebClient.builder()
                .filter(filter);
    }


    @Bean
    public NotificatorClient notificatorClient(@Value("${app.base-url.notificator}") String notificatorBaseUrl,
                                               WebClient.Builder servicesWebClientBuilder) {
        return new NotificatorClient(servicesWebClientBuilder.baseUrl(notificatorBaseUrl)
                .build());
    }

    @Bean
    public HeadManagerClient headManagerClient(@Value("${app.base-url.head-manager}") String headManagerBaseUrl,
                                               WebClient.Builder servicesWebClientBuilder) {
        return new HeadManagerClient(servicesWebClientBuilder.baseUrl(headManagerBaseUrl)
                .build());
    }
}
