package com.annobot.chatbot.model;

@Deprecated
public enum MessageType {
    INTRODUCTION,
    ASK_ABOUT_NAME,
    NAME_ANSWER,
    NAME_COMMENT,
    ASK_ABOUT_AGE,
    AGE_ANSWER,
    AGE_COMMENT,
    ASK_FOR_HELP,
    NOT_INTERESTED_IN_ANNOTATING,
    PRE_ANNOTATION,
    ITEM_TO_ANNOTATE,
    ITEM_LABEL,
    THANKS_FOR_ANNOTATING,
    ASK_ABOUT_REASON,
    ANSWER,
    I_DONT_KNOW;

    public static MessageType getAnswerType(MessageType messageType) {
        switch (messageType) {
            case ASK_ABOUT_NAME:
                return NAME_ANSWER;
            case ASK_ABOUT_AGE:
                return AGE_ANSWER;
            case ITEM_TO_ANNOTATE:
                return ITEM_LABEL;
            default:
                return ANSWER;
        }
    }
}
