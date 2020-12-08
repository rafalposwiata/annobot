export enum Type {
    SIMPLE_MESSAGE = "SIMPLE_MESSAGE",
    ASK_ABOUT_SOMETHING = "ASK_ABOUT_SOMETHING",
    DATASET = "DATASET",
    MODEL_PREDICTION = "MODEL_PREDICTION"
}

export enum UserInputType {
    LABEL = "LABEL",
    NUMBER = "NUMBER",
    TEXT = "TEXT",
    IMAGE = "IMAGE"
}

export enum LabelingSchema {
    BINARY = "BINARY",
    MULTICLASS = "MULTICLASS",
    MULTILABEL = "MULTILABEL"
}

export enum NextStepCondition {
    AFTER_COMPLETE = "AFTER_COMPLETE",
    AFTER_N_SAMPLES = "AFTER_N_SAMPLES"
}

export enum ExternalClient {
    FACEBOOK = "FACEBOOK"
}

export interface ExternalClientConfig {
    type: ExternalClient;
}

export interface FacebookConfig extends ExternalClientConfig {
    verifyToken?: string;
    pageAccessToken?: string;
}

export interface NextStep {
    condition: NextStepCondition;
    n?: number;
    elementName: string;
}

export interface ConversationSchemaElement {
    id: number;
    name: string;
    type: Type;
    nextSteps: NextStep[];
}

export interface SimpleMessageElement extends ConversationSchemaElement {
    possibleMessages: string;
    waitForAnswer: boolean;
}

export interface AskAboutSomethingElement extends SimpleMessageElement {
    validator?: string;
    validationMessage?: string;
}

export interface DatasetElement extends ConversationSchemaElement {
    datasetId: number;
    userInputType?: UserInputType;
    labelingSchema?: LabelingSchema;
    labels: Label[];
    instruction?: string;
    afterEachSample?: string;
    numberOfSamples?: number;
}

export interface ModelPredictionElement extends ConversationSchemaElement {
    modelName: string;
    datasetId?: number;
    introduction?: string;
    messageWithModelPrediction?: string;
    numberOfRepetitions?: number;
}

export interface BotConfiguration {
    name: string;
    mlModelUrl?: string;
    externalClientsConfigs: ExternalClientConfig[];
    conversationSchema: ConversationSchemaElement[];
}

export interface Label {
    value: string;
    abbr?: string;
}

export interface DeleteBotRequest {
    botName: string;
}