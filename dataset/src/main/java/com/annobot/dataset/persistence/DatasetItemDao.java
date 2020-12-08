package com.annobot.dataset.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetItemDao extends JpaRepository<DatasetItemEntity, Long> {

    @Query(value = "select item from dataset_item item where item.datasetId = :datasetId")
    List<DatasetItemEntity> getDatasetItems(@Param("datasetId") Long datasetId);
}
