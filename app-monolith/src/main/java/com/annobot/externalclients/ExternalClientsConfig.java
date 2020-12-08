package com.annobot.externalclients;

import com.annobot.externalclients.facebook.FacebookApi;
import com.annobot.externalclients.facebook.FacebookService;
import com.annobot.externalclients.integration.ChatbotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalClientsConfig {

    @Value("${external-clients.facebook.graph-api}")
    private String graphApi;

    @Bean
    public FacebookApi facebookApi() {
        return new FacebookApi(graphApi);
    }

    @Bean
    public FacebookService facebookService(FacebookApi facebookApi,
                                           ChatbotClient chatbotClient,
                                           RestTemplate restTemplate) {
        return new FacebookService(facebookApi, chatbotClient, restTemplate);
    }
}
