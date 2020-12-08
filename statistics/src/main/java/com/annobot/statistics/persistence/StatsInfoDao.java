package com.annobot.statistics.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsInfoDao extends JpaRepository<StatsInfoEntity, Long> {


}
