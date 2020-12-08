package com.annobot.statistics;

import com.annobot.statistics.model.StatsInfo;
import com.annobot.statistics.persistence.StatsInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class StatisticsEndpoint {

    private final StatsInfoRepository statsInfoRepository;

    @Autowired
    public StatisticsEndpoint(StatsInfoRepository statsInfoRepository) {
        this.statsInfoRepository = statsInfoRepository;
    }

    @PostMapping(value = "/statistics")
    public String upload(@RequestBody StatsInfo statsInfo) {
        try {
            statsInfoRepository.save(statsInfo);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
