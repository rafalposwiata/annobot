package com.annobot.chatbot.model.config;

import com.annobot.chatbot.model.config.externalclients.ExternalClient;
import com.annobot.chatbot.model.config.externalclients.ExternalClientConfig;
import com.annobot.chatbot.model.config.externalclients.FacebookConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatbotConfiguration implements Serializable {

    private String name;
    private String mlModelUrl;
    private List<? extends ExternalClientConfig> externalClientsConfigs = new ArrayList<>();
    private List<? extends ConversationSchemaElement> conversationSchema = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMlModelUrl() {
        return mlModelUrl;
    }

    public void setMlModelUrl(String mlModelUrl) {
        this.mlModelUrl = mlModelUrl;
    }

    public List<? extends ExternalClientConfig> getExternalClientsConfigs() {
        return externalClientsConfigs;
    }

    public void setExternalClientsConfigs(List<? extends ExternalClientConfig> externalClientsConfigs) {
        this.externalClientsConfigs = externalClientsConfigs;
    }

    public List<? extends ConversationSchemaElement> getConversationSchema() {
        return conversationSchema;
    }

    public void setConversationSchema(List<? extends ConversationSchemaElement> conversationSchema) {
        this.conversationSchema = conversationSchema;
    }

    public FacebookConfig facebookConfig() {
        return externalClientsConfigs
                .stream()
                .filter(ecc -> ecc.getType().equals(ExternalClient.FACEBOOK))
                .findFirst()
                .map(ecc -> (FacebookConfig) ecc)
                .orElse(new FacebookConfig());
    }
}
