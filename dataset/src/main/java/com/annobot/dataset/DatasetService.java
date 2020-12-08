package com.annobot.dataset;

import com.annobot.dataset.model.DatasetUploadResponse;
import com.annobot.dataset.model.DeleteDatasetRequest;
import org.springframework.web.multipart.MultipartFile;

public interface DatasetService {

    DatasetUploadResponse upload(String datasetName, MultipartFile file);

    void saveAnnotation(Long datasetId, Long datasetItemId, String conversationId, String userInputType,
                        String userInput);

    String delete(DeleteDatasetRequest deleteDatasetRequest);
}
