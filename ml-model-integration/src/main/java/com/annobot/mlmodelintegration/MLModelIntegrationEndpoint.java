package com.annobot.mlmodelintegration;

import com.annobot.mlmodelintegration.model.Models;
import com.annobot.mlmodelintegration.model.PredictionRequest;
import com.annobot.mlmodelintegration.model.PredictionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/ml-model")
public class MLModelIntegrationEndpoint {

    private MLModelIntegrationService modelIntegrationService;

    @Autowired
    public MLModelIntegrationEndpoint(MLModelIntegrationService modelIntegrationService) {
        this.modelIntegrationService = modelIntegrationService;
    }

    @GetMapping("/models")
    public Models getModels(@RequestParam("url") String url) {
        return modelIntegrationService.getModels(url);
    }

    @PostMapping("/predict/{modelName}")
    public PredictionResult predict(@PathVariable("modelName") String modelName,
                                    @RequestParam("url") String url,
                                    @RequestBody PredictionRequest predictRequest) {
        return modelIntegrationService.predict(url, modelName, predictRequest);
    }
}
