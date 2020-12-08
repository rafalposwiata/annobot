package com.annobot.dataset;

import com.annobot.dataset.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
public class DatasetEndpoint {

    private DatasetService datasetService;
    private DatasetProjection datasetProjection;

    @Autowired
    public DatasetEndpoint(DatasetService datasetService, DatasetProjection datasetProjection) {
        this.datasetService = datasetService;
        this.datasetProjection = datasetProjection;
    }

    @PostMapping(value = "/dataset/upload/{datasetName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    DatasetUploadResponse upload(@PathVariable String datasetName, @RequestParam MultipartFile file) {
        return datasetService.upload(datasetName, file);
    }

    @GetMapping(value = "/dataset/download/{datasetId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<FileSystemResource> download(@PathVariable Long datasetId) {
        DatasetDownloadResponse downloadResponse = datasetProjection.download(datasetId);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadResponse.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(downloadResponse.getFile());
    }

    @GetMapping(value = "/dataset")
    public @ResponseBody
    List<DatasetShortInfo> getDatasets() {
        return datasetProjection.getDatasets();
    }

    @DeleteMapping(value = "/dataset")
    public @ResponseBody
    String delete(@RequestBody DeleteDatasetRequest deleteDatasetRequest) {
        return datasetService.delete(deleteDatasetRequest);
    }

    @GetMapping(value = "/dataset/{datasetId}")
    public @ResponseBody
    DatasetInfo getDatasetItems(@PathVariable Long datasetId) {
        return datasetProjection.getDatasetInfo(datasetId);
    }
}
