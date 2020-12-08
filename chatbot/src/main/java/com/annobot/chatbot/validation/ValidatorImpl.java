package com.annobot.chatbot.validation;

import com.annobot.chatbot.model.Message;
import org.apache.commons.lang3.StringUtils;

public class ValidatorImpl implements Validator {

    @Override
    public boolean validate(Message message, String validatorName) {
        if ("number".equals(validatorName)) {
            return StringUtils.isNumeric(message.getText());
        }
        return true;
    }
}
