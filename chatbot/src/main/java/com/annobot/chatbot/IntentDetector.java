package com.annobot.chatbot;

import com.annobot.chatbot.model.Intent;
import com.annobot.chatbot.model.Message;

public interface IntentDetector {

    Intent detect(Message message);
}
