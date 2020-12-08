package com.annobot.dataset;

import com.annobot.dataset.model.*;
import com.annobot.dataset.persistence.*;
import org.springframework.core.io.FileSystemResource;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.stream.Collectors.toList;

public class DatasetProjection {

    private final DatasetInfoDao datasetInfoDao;
    private final DatasetItemDao datasetItemDao;
    private final DatasetItemAnnotationDao datasetItemAnnotationDao;
    private final AnnotationService annotationService;

    public DatasetProjection(DatasetInfoDao datasetInfoDao, DatasetItemDao datasetItemDao,
                             DatasetItemAnnotationDao datasetItemAnnotationDao, AnnotationService annotationService) {
        this.datasetInfoDao = datasetInfoDao;
        this.datasetItemDao = datasetItemDao;
        this.datasetItemAnnotationDao = datasetItemAnnotationDao;
        this.annotationService = annotationService;
    }

    public DatasetItemEntity get(Long datasetId, Integer idx) {
        try {
            return datasetItemDao.getDatasetItems(datasetId).get(idx);
        } catch (Exception e) {
            return null;
        }
    }

    public List<DatasetShortInfo> getDatasets() {
        return datasetInfoDao
                .findAll()
                .stream()
                .map(entity -> new DatasetShortInfo(
                        entity.getDatasetId(),
                        entity.getName()))
                .collect(toList());
    }

    @Transactional
    public DatasetInfo getDatasetInfo(Long datasetId) {
        DatasetInfoEntity infoEntity = datasetInfoDao.getOne(datasetId);
        List<DatasetItem> items = getItems(datasetId);
        IAA iaa = getIAA(items);
        return new DatasetInfo(
                infoEntity.getDatasetId(),
                infoEntity.getName(),
                items,
                iaa);
    }

    @Transactional
    public DatasetDownloadResponse download(Long datasetId) {
        try {
            DatasetInfoEntity info = datasetInfoDao.getOne(datasetId);
            List<String> items = getItems(datasetId)
                    .stream()
                    .filter(DatasetItem::hasAnnotations)
                    .map(DatasetItem::toCSV)
                    .collect(toList());


            Path tempFile = Files.createTempFile(info.getName(), ".csv");
            Files.write(tempFile, newArrayList("Text;Label;Annotations"), APPEND);
            Files.write(tempFile, items, APPEND);
            return new DatasetDownloadResponse(info.getName() + ".csv", new FileSystemResource(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<DatasetItem> getItems(Long datasetId) {
        return datasetItemDao
                .getDatasetItems(datasetId)
                .stream()
                .map(this::transform)
                .collect(toList());
    }

    private IAA getIAA(List<DatasetItem> items) {
        return annotationService.getIAA(items
                .stream()
                .flatMap(item -> item.getAnnotations().stream())
                .collect(toList()));
    }

    private DatasetItem transform(DatasetItemEntity entity) {
        List<DatasetItemAnnotation> annotations = getAnnotations(entity.getDatasetId(), entity.getDatasetItemId());
        String goldLabel = annotationService.getGoldLabel(annotations);
        return new DatasetItem(
                entity.getDatasetItemId(),
                entity.getDatasetId(),
                entity.getContent(),
                goldLabel,
                annotations);
    }

    @Transactional
    public List<DatasetItemAnnotation> getAnnotations(Long datasetId, Long datasetItemId) {
        return datasetItemAnnotationDao
                .getDatasetItemAnnotations(datasetId, datasetItemId)
                .stream()
                .map(entity -> new DatasetItemAnnotation(
                        entity.getDatasetItemId(),
                        entity.getConversationId(),
                        entity.getUserInput()))
                .collect(toList());
    }
}
