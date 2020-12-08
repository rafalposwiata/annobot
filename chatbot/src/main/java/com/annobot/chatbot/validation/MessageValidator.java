package com.annobot.chatbot.validation;

import com.annobot.chatbot.model.Message;

public interface MessageValidator {

    boolean validate(Message message);
}
