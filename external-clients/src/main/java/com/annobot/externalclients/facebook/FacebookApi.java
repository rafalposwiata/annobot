package com.annobot.externalclients.facebook;

public class FacebookApi {

    private final String graphApi;

    public FacebookApi(String graphApi) {
        this.graphApi = graphApi;
    }

    public String getGraphApi() {
        return graphApi;
    }

    public String getUserApi() {
        return graphApi + "/{userId}?access_token={token}";
    }

    public String getSubscribeUrl() {
        return graphApi + "/me/subscribed_apps";
    }

    public String getSendUrl(String pageAccessToken) {
        return graphApi + "/me/messages?access_token=" + pageAccessToken;
    }

    public String getMessengerProfileUrl(String pageAccessToken) {
        return graphApi + "/me/messenger_profile?access_token=" + pageAccessToken;
    }
}
