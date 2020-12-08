package com.annobot.dataset.model;

import java.io.Serializable;

public class DatasetUploadResponse implements Serializable {

    private String status;

    public DatasetUploadResponse() {
    }

    public DatasetUploadResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
