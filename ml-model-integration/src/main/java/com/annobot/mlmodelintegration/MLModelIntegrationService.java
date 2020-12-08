package com.annobot.mlmodelintegration;

import com.annobot.mlmodelintegration.model.Models;
import com.annobot.mlmodelintegration.model.PredictionRequest;
import com.annobot.mlmodelintegration.model.PredictionResult;
import org.springframework.web.client.RestTemplate;

public class MLModelIntegrationService {

    private final RestTemplate restTemplate;

    public MLModelIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Models getModels(String mlModelUrl){
        return restTemplate.getForEntity(mlModelUrl + "/models", Models.class).getBody();
    }

    public PredictionResult predict(String mlModelUrl, String modelName, PredictionRequest predictRequest) {
        return restTemplate.postForEntity(mlModelUrl +"/predict/"+ modelName, predictRequest,
                PredictionResult.class).getBody();
    }
}
