package com.annobot.chatbot.model.config.externalclients;

public class FacebookConfig extends ExternalClientConfig {

    private String verifyToken;
    private String pageAccessToken;

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getPageAccessToken() {
        return pageAccessToken;
    }

    public void setPageAccessToken(String pageAccessToken) {
        this.pageAccessToken = pageAccessToken;
    }
}
