package com.annobot.chatbot.integration;

import com.annobot.mlmodelintegration.MLModelIntegrationService;
import com.annobot.mlmodelintegration.model.PredictionRequest;

public class MLModelClientImpl implements MLModelClient {

    private MLModelIntegrationService modelIntegrationService;
    private String mlModelUrl;

    public MLModelClientImpl(MLModelIntegrationService modelIntegrationService, String mlModelUrl) {
        this.modelIntegrationService = modelIntegrationService;
        this.mlModelUrl = mlModelUrl;
    }

    @Override
    public String predict(String modelName, String text) {
        return modelIntegrationService.predict(mlModelUrl, modelName, new PredictionRequest(text)).getResult();
    }
}
