package com.annobot.dataset.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetItemAnnotationDao extends JpaRepository<DatasetItemAnnotationEntity, Long> {

    @Query(value = "select ann from dataset_item_annotation ann " +
            "where ann.datasetId = :datasetId and ann.datasetItemId = :datasetItemId")
    List<DatasetItemAnnotationEntity> getDatasetItemAnnotations(@Param("datasetId") Long datasetId,
                                                                @Param("datasetItemId") Long datasetItemId);
}
