package com.pim.projects.besttravel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${api.base.url}")
    private String baseUrl;

    @Value(value = "${api.api-key}")
    private String apiKey;

    @Value(value = "${api.api-hey-header}")
    private String apiKeyHeader;


    @Bean
    public WebClient webClient(){
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apiKey)
                .build();
    }


}
