package com.annobot.dataset.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetInfoDao extends JpaRepository<DatasetInfoEntity, Long> {

    @Query(value = "select name from dataset.dataset_info", nativeQuery = true)
    List<String> getDatasetNames();
}
