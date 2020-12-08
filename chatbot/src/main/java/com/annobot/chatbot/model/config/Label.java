package com.annobot.chatbot.model.config;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class Label {

    private String value;
    private String abbr;

    public Label(){}

    public Label(String value, String abbr) {
        this.value = value;
        this.abbr = abbr;
    }

    public boolean correct(String text) {
        if (text == null) return false;
        return containsIgnoreCase(text, value) || equalsIgnoreCase(text, abbr);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Override
    public String toString() {
        if (abbr == null) return value;
        return format("%s [%s]", value, abbr);
    }
}
