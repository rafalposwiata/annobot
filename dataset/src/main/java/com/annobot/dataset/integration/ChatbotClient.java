package com.annobot.dataset.integration;

import java.util.List;

public interface ChatbotClient {

    List<String> annotationForUser(String userId);
}
