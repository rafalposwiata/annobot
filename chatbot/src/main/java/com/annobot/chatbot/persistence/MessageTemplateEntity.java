package com.annobot.chatbot.persistence;

import com.annobot.chatbot.model.MessageType;

import javax.persistence.*;

@Entity(name = "message_template")
@Table(schema = "chatbot")
public class MessageTemplateEntity {

    @Id
    @Column(name = "message_template_id")
    private Long messageTemplateId;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "content")
    private String content;

    public Long getMessageTemplateId() {
        return messageTemplateId;
    }

    public void setMessageTemplateId(Long messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
