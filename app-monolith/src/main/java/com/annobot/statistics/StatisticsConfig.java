package com.annobot.statistics;

import com.annobot.statistics.persistence.StatsInfoDao;
import com.annobot.statistics.persistence.StatsInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfig {

    @Bean
    public StatsInfoRepository statsInfoRepository(StatsInfoDao statsInfoDao) {
        return new StatsInfoRepository(statsInfoDao);
    }
}
