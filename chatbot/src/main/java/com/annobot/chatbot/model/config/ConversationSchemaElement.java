package com.annobot.chatbot.model.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes({
        @Type(value = SimpleMessageElement.class, name = "SIMPLE_MESSAGE"),
        @Type(value = AskAboutSomethingElement.class, name = "ASK_ABOUT_SOMETHING"),
        @Type(value = DatasetElement.class, name = "DATASET"),
        @Type(value = ModelPredictionElement.class, name = "MODEL_PREDICTION")
})
public abstract class ConversationSchemaElement {

    private Long id;
    private String name;
    private ConversationSchemaElementType type;
    private List<NextStep> nextSteps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConversationSchemaElementType getType() {
        return type;
    }

    public void setType(ConversationSchemaElementType type) {
        this.type = type;
    }

    public List<NextStep> getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(List<NextStep> nextSteps) {
        this.nextSteps = nextSteps;
    }
}
