import {Label, LabelingSchema, UserInputType} from "../../bot/model/bot_model";

export interface Message {
    text: string;
    conversationId: string,
    messageType?: string,
    itemId?: string;
    dataItem?: boolean;
    botPrediction?: boolean;
    userInputType?: UserInputType;
    userMessage?: boolean;
    label?: string;
    labelingSchema?: LabelingSchema;
    labels?: Label[];
}