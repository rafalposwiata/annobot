package com.annobot.chatbot.validation;

import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.config.ConversationSchemaElement;

public interface Validator {

    boolean validate(Message message, String validatorName);
}
