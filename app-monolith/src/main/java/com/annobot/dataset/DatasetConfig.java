package com.annobot.dataset;

import com.annobot.dataset.integration.ChatbotClient;
import com.annobot.dataset.persistence.DatasetInfoDao;
import com.annobot.dataset.persistence.DatasetItemAnnotationDao;
import com.annobot.dataset.persistence.DatasetItemDao;
import com.annobot.dataset.persistence.DatasetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasetConfig {

    @Bean
    public DatasetRepository datasetRepository(DatasetInfoDao datasetInfoDao, DatasetItemDao datasetItemDao,
                                               DatasetItemAnnotationDao datasetItemAnnotationDao) {
        return new DatasetRepository(datasetInfoDao, datasetItemDao, datasetItemAnnotationDao);
    }

    @Bean
    public DatasetService datasetService(DatasetRepository datasetRepository,
                                         ChatbotClient chatbotClient) {
        return new DatasetServiceImpl(datasetRepository, chatbotClient);
    }

    @Bean
    public AnnotationService annotationService() {
        return new AnnotationServiceImpl();
    }

    @Bean
    public DatasetProjection datasetProjection(DatasetInfoDao datasetInfoDao, DatasetItemDao datasetItemDao,
                                               DatasetItemAnnotationDao datasetItemAnnotationDao,
                                               AnnotationService annotationService) {
        return new DatasetProjection(datasetInfoDao, datasetItemDao, datasetItemAnnotationDao, annotationService);
    }
}
