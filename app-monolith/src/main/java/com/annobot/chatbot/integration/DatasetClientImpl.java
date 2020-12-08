package com.annobot.chatbot.integration;

import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.Record;
import com.annobot.dataset.DatasetProjection;
import com.annobot.dataset.DatasetService;
import com.annobot.dataset.persistence.DatasetItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetClientImpl implements DatasetClient {

    private DatasetProjection datasetProjection;
    private DatasetService datasetService;

    @Autowired
    public DatasetClientImpl(DatasetProjection datasetProjection, DatasetService datasetService) {
        this.datasetProjection = datasetProjection;
        this.datasetService = datasetService;
    }

    @Override
    public Record get(Long datasetId, Integer idx) {
        DatasetItemEntity datasetItemEntity = datasetProjection.get(datasetId, idx);
        if (datasetItemEntity == null)
            return null;
        return new Record(datasetItemEntity.getDatasetItemId(), idx, datasetItemEntity.getContent());
    }

    @Override
    public void saveAnnotation(Message message) {
        this.datasetService.saveAnnotation(message.getDatasetId(), message.getItemId(), message.getConversationId(),
                message.getUserInputType().name(), message.getText());
    }
}
