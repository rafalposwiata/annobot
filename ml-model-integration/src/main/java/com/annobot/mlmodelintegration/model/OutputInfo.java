package com.annobot.mlmodelintegration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputInfo {

    private OutputType type;
    @JsonProperty("possible_labels")
    private List<String> possibleLabels;

    public OutputType getType() {
        return type;
    }

    public void setType(OutputType type) {
        this.type = type;
    }

    public List<String> getPossibleLabels() {
        return possibleLabels;
    }

    public void setPossibleLabels(List<String> possibleLabels) {
        this.possibleLabels = possibleLabels;
    }
}
