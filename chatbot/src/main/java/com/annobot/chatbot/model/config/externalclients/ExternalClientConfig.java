package com.annobot.chatbot.model.config.externalclients;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FacebookConfig.class, name = "FACEBOOK")
})
public abstract class ExternalClientConfig {

    private ExternalClient type;

    public ExternalClient getType() {
        return type;
    }

    public void setType(ExternalClient type) {
        this.type = type;
    }
}
