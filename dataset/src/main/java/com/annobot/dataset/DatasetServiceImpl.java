package com.annobot.dataset;

import com.annobot.dataset.integration.ChatbotClient;
import com.annobot.dataset.model.DatasetUploadResponse;
import com.annobot.dataset.model.DeleteDatasetRequest;
import com.annobot.dataset.persistence.DatasetRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DatasetServiceImpl implements DatasetService {

    private final DatasetRepository datasetRepository;
    private final ChatbotClient chatbotClient;

    DatasetServiceImpl(DatasetRepository datasetRepository, ChatbotClient chatbotClient) {
        this.datasetRepository = datasetRepository;
        this.chatbotClient = chatbotClient;
    }

    @Override
    public DatasetUploadResponse upload(String datasetName, MultipartFile file) {
        Long datasetId = datasetRepository.saveInfo(datasetName);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            reader.lines().forEach(line -> datasetRepository.saveItem(datasetId, line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DatasetUploadResponse("200");
    }

    @Override
    public void saveAnnotation(Long datasetId, Long datasetItemId, String conversationId, String userInputType,
                               String userInput) {
        datasetRepository.saveAnnotation(datasetId, datasetItemId, conversationId, userInputType, userInput);
    }

    @Override
    public String delete(DeleteDatasetRequest deleteDatasetRequest) {
        datasetRepository.delete(deleteDatasetRequest.getDatasetId());
        return "OK";
    }
}
