import {Message} from "../model/chat_model";
import {api} from "../../api";

class ChatService {

    public initConversation(botName: string, userId: string, mobile: boolean): Promise<Message[]> {
        return api.get("/chatbot/init/" + botName + "/" + userId + "?mobile=" + mobile)
    }

    public sendMessage(message: Message): Promise<Message[]> {
        return api.post("/chatbot", message);
    }
}

export const chatService = new ChatService();