import {api} from "../../api";
import {BotConfiguration} from "../model/bot_model";

class BotService {

    public saveConfig(botConfig: BotConfiguration): Promise<string> {
        return api.post("/chatbot/config", botConfig);
    }

    public deleteConfig(botName: string): Promise<string> {
        return api.delete("/chatbot/config", {botName: botName});
    }

    public getConfig(botName: string): Promise<BotConfiguration> {
        return api.get("/chatbot/config/" + botName);
    }

    public getChatbots(): Promise<string[]> {
        return api.get("/chatbot/bots");
    }
}

export const botService = new BotService();