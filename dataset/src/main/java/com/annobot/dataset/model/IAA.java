package com.annobot.dataset.model;

public class IAA {

    private Double value;
    private Boolean unknown = false;

    public IAA(Double value) {
        this.value = value;
    }

    public IAA(Boolean unknown) {
        this.unknown = unknown;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getUnknown() {
        return unknown;
    }

    public void setUnknown(Boolean unknown) {
        this.unknown = unknown;
    }
}
