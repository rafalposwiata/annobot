package com.annobot.chatbot;

import com.annobot.chatbot.model.DeleteBotRequest;
import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.config.ChatbotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ChatbotEndpoint {

    private final Chatbot chatbot;
    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotEndpoint(Chatbot chatbot, ChatbotService chatbotService) {
        this.chatbot = chatbot;
        this.chatbotService = chatbotService;
    }

    @GetMapping(value = "/chatbot/init/{botName}/{user}")
    public @ResponseBody
    List<Message> init(@PathVariable("botName") String botName, @PathVariable("user") String user,
                       @RequestParam("mobile") boolean mobile) {
        return chatbot.init(botName, user, mobile);
    }

    @PostMapping(value = "/chatbot")
    public @ResponseBody
    List<Message> receiveMessage(@RequestBody Message message) {
        return chatbot.receiveMessage(message);
    }

    @PostMapping(value = "/chatbot/config")
    public @ResponseBody
    String save(@RequestBody ChatbotConfiguration chatbotConfiguration) {
        return chatbotService.save(chatbotConfiguration);
    }

    @DeleteMapping(value = "/chatbot/config")
    public @ResponseBody
    String delete(@RequestBody DeleteBotRequest deleteBotRequest) {
        return chatbotService.delete(deleteBotRequest);
    }

    @GetMapping(value = "/chatbot/config/{name}")
    public @ResponseBody
    ChatbotConfiguration getConfig(@PathVariable("name") String name) {
        return chatbotService.getConfig(name);
    }

    @GetMapping(value = "/chatbot/bots")
    public @ResponseBody
    List<String> getChatbots() {
        return chatbotService.getChatbots();
    }
}
