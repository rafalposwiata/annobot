package com.annobot.dataset.model;

import org.springframework.core.io.FileSystemResource;

import java.io.Serializable;

public class DatasetDownloadResponse implements Serializable {

    private String name;
    private FileSystemResource file;

    public DatasetDownloadResponse(String name, FileSystemResource file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public FileSystemResource getFile() {
        return file;
    }
}
