package com.annobot.chatbot.model;

public class Record {

    private Long id;
    private Integer idx;
    private String text;

    public Record() {
    }

    public Record(Long id, Integer idx, String text) {
        this.id = id;
        this.idx = idx;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

