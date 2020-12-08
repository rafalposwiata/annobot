package com.annobot.chatbot.integration;

import com.annobot.mlmodelintegration.MLModelIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MLModelFactoryImpl implements MLModelFactory {

    private MLModelIntegrationService mlModelIntegrationService;

    @Autowired
    public MLModelFactoryImpl(MLModelIntegrationService mlModelIntegrationService) {
        this.mlModelIntegrationService = mlModelIntegrationService;
    }

    @Override
    public MLModelClient create(String mlModelUrl) {
        if (mlModelUrl == null) return null;
        return new MLModelClientImpl(mlModelIntegrationService, mlModelUrl);
    }
}
