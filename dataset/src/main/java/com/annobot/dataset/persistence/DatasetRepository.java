package com.annobot.dataset.persistence;

import javax.transaction.Transactional;

public class DatasetRepository {

    private final DatasetInfoDao datasetInfoDao;
    private final DatasetItemDao datasetItemDao;
    private final DatasetItemAnnotationDao datasetItemAnnotationDao;

    public DatasetRepository(DatasetInfoDao datasetInfoDao, DatasetItemDao datasetItemDao,
                             DatasetItemAnnotationDao datasetItemAnnotationDao) {
        this.datasetInfoDao = datasetInfoDao;
        this.datasetItemDao = datasetItemDao;
        this.datasetItemAnnotationDao = datasetItemAnnotationDao;
    }

    @Transactional
    public Long saveInfo(String datasetName) {
        DatasetInfoEntity entity = new DatasetInfoEntity(datasetName);
        entity = datasetInfoDao.save(entity);
        return entity.getDatasetId();
    }

    @Transactional
    public void saveItem(Long datasetId, String content) {
        datasetItemDao.save(new DatasetItemEntity(datasetId, content));
    }

    @Transactional
    public void saveAnnotation(Long datasetId, Long datasetItemId, String conversationId, String userInputType,
                               String userInput) {
        datasetItemAnnotationDao.save(new DatasetItemAnnotationEntity(datasetId, datasetItemId, conversationId,
                userInputType, userInput));
    }

    @Transactional
    public void delete(Long datasetId) {
        DatasetInfoEntity entity = datasetInfoDao.getOne(datasetId);
        datasetInfoDao.delete(entity);
    }
}
