package com.annobot.mlmodelintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MLModelIntegrationConfig {

    @Bean
    public MLModelIntegrationService modelIntegrationService(RestTemplate restTemplate) {
        return new MLModelIntegrationService(restTemplate);
    }
}
