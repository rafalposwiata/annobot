package com.annobot.statistics.persistence;

import com.annobot.statistics.model.StatsInfo;

import javax.transaction.Transactional;

public class StatsInfoRepository {

    private final StatsInfoDao statsInfoDao;

    public StatsInfoRepository(StatsInfoDao statsInfoDao) {
        this.statsInfoDao = statsInfoDao;
    }

    @Transactional
    public void save(StatsInfo statsInfo) {
        statsInfoDao.save(new StatsInfoEntity(statsInfo));
    }
}
