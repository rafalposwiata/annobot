package com.annobot.chatbot.persistence;

import com.annobot.chatbot.model.Message;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "message")
@Table(schema = "chatbot")
public class MessageEntity {

    private final static String MESSAGE_ENTITY_SEQ = "chatbot.message_message_id_seq";

    @Id
    @SequenceGenerator(name = MESSAGE_ENTITY_SEQ, sequenceName = MESSAGE_ENTITY_SEQ, allocationSize = 1)
    @GeneratedValue(generator = MESSAGE_ENTITY_SEQ)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "bot_name")
    private String botName;

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "content")
    private String content;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "conversation_id")
    private String conversationId;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public MessageEntity() {
    }

    public MessageEntity(Message message, LocalDateTime createDate) {
        this.messageType = message.getMessageType();
        this.botName = message.getBotName();
        this.content = message.isDataItem() ? null : message.getText();
        this.itemId = String.valueOf(message.getItemId());
        this.conversationId = message.getConversationId();
        this.createDate = createDate;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getBotName() {
        return botName;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public String getItemId() {
        return itemId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}