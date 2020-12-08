package com.annobot.chatbot.persistence;

import com.annobot.chatbot.model.config.ChatbotConfiguration;

import javax.persistence.*;

@Entity(name = "config")
@Table(schema = "chatbot")
public class ConfigEntity {

    private final static String CONFIG_ENTITY_SEQ = "chatbot.config_config_id_seq";

    @Id
    @SequenceGenerator(name = CONFIG_ENTITY_SEQ, sequenceName = CONFIG_ENTITY_SEQ, allocationSize = 1)
    @GeneratedValue(generator = CONFIG_ENTITY_SEQ)
    @Column(name = "config_id")
    private Long configId;

    @Column(name = "bot_name")
    private String botName;

    @Column(name = "configuration")
    private String configuration;

    public ConfigEntity() {
    }

    public ConfigEntity(String botName) {
        this.botName = botName;
    }

    public String getBotName() {
        return botName;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
