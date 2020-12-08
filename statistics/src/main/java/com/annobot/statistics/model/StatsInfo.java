package com.annobot.statistics.model;

public class StatsInfo {

    private String userName;
    private String systemName;
    private String itemId;
    private String description;
    private String timeDiff;

    public StatsInfo(){}

    public StatsInfo(String userName, String systemName, String itemId, String description, String timeDiff) {
        this.userName = userName;
        this.systemName = systemName;
        this.itemId = itemId;
        this.description = description;
        this.timeDiff = timeDiff;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }
}
