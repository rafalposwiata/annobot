package com.annobot.externalclients.facebook;

import com.annobot.externalclients.facebook.model.*;
import com.annobot.externalclients.integration.ChatbotClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.annobot.externalclients.facebook.model.EventType.*;

public class FacebookService {

    private static final Logger logger = LoggerFactory.getLogger(FacebookService.class);

    private final FacebookApi facebookApi;
    private final ChatbotClient chatbotClient;
    private final RestTemplate restTemplate;

    public FacebookService(FacebookApi facebookApi, ChatbotClient chatbotClient, RestTemplate restTemplate) {
        this.facebookApi = facebookApi;
        this.chatbotClient = chatbotClient;
        this.restTemplate = restTemplate;
    }

    public boolean verify(String botName, String verifyToken, String mode) {
        return getVerifyToken(botName).equals(verifyToken) && SUBSCRIBE.name().equalsIgnoreCase(mode);
    }

    public ResponseEntity receiveMessage(String botName, Callback callback) {
        try {
            if (!callback.getObject().equals("page")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            for (Entry entry : callback.getEntry()) {
                if (entry.getMessaging() != null) {
                    for (Event event : entry.getMessaging()) {
//                        logger.debug("Received: {}", event);
                        setType(event);
                        if (event.getType() == null) {
                            logger.debug("Callback/Event type not supported: {}", event);
                            return ResponseEntity.ok("Callback not supported yet!");
                        } else if (MESSAGE.equals(event.getType()) || QUICK_REPLY.equals(event.getType())) {
                            sendTypingIndicator(botName, event.getSender(), "typing_on");
                            reply(botName, event);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in fb webhook: Callback: {} \nException: ", callback.toString(), e);
        }
        return ResponseEntity.ok("EVENT_RECEIVED");
    }

    private void reply(String botName, Event event) {
        for (Message message : chatbotClient.sendToBot(botName, event)) {
            Event response = new Event()
                    .setMessagingType("RESPONSE")
                    .setRecipient(event.getSender())
                    .setMessage(message);

            sendTypingIndicator(botName, response.getRecipient(), "typing_off");
            logger.info("Send message: {}", response.toString());
            try {
                restTemplate.postForEntity(getSendUrl(botName), response, String.class);
            } catch (HttpClientErrorException e) {
                logger.error("Send message error: Response body: {} \nException: ", e.getResponseBodyAsString(), e);
            }
        }
    }

    private void sendTypingIndicator(String botName, User recipient, String senderAction) {
        Event event = new Event()
                .setRecipient(recipient)
                .setSenderAction(senderAction);
        restTemplate.postForEntity(getSendUrl(botName), event, Response.class);
    }

    private void setType(Event event) {
        if (event.getMessage() != null) {
            if (event.getMessage().isEcho() != null &&
                    event.getMessage().isEcho()) {
                event.setType(MESSAGE_ECHO);
            } else if (event.getMessage().getQuickReply() != null) {
                event.setType(QUICK_REPLY);
            } else {
                event.setType(MESSAGE);
            }
        } else if (event.getDelivery() != null) {
            event.setType(MESSAGE_DELIVERED);
        } else if (event.getRead() != null) {
            event.setType(MESSAGE_READ);
        } else if (event.getPostback() != null) {
            event.setType(POSTBACK);
        } else if (event.getOptin() != null) {
            event.setType(OPT_IN);
        } else if (event.getReferral() != null) {
            event.setType(REFERRAL);
        } else if (event.getAccountLinking() != null) {
            event.setType(ACCOUNT_LINKING);
        }
    }

    private String getSendUrl(String botName) {
        String accessToken = getAccessToken(botName);
        return facebookApi.getSendUrl(accessToken);
    }

    private String getVerifyToken(String botName) {
        return chatbotClient.getFacebookTokens(botName).getVerifyToken();
    }

    private String getAccessToken(String botName) {
        return chatbotClient.getFacebookTokens(botName).getPageAccessToken();
    }
}
