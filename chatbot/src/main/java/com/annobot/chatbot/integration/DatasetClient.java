package com.annobot.chatbot.integration;

import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.Record;

public interface DatasetClient {

    Record get(Long datasetId, Integer idx);

    void saveAnnotation(Message message);
}
