package com.annobot.externalclients.integration.model;

public class FacebookTokens {

    private String verifyToken;
    private String pageAccessToken;

    public FacebookTokens() {
    }

    public FacebookTokens(String verifyToken, String pageAccessToken) {
        this.verifyToken = verifyToken;
        this.pageAccessToken = pageAccessToken;
    }

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
