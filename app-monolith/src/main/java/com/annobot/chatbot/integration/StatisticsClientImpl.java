package com.annobot.chatbot.integration;

import com.annobot.statistics.model.StatsInfo;
import com.annobot.statistics.persistence.StatsInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsClientImpl implements StatisticsClient {

    private StatsInfoRepository statsInfoRepository;

    @Autowired
    public StatisticsClientImpl(StatsInfoRepository statsInfoRepository) {
        this.statsInfoRepository = statsInfoRepository;
    }

    @Override
    public void send(String userName, String itemId, String description, String timeDiff, boolean mobile) {
        try {
            statsInfoRepository.save(new StatsInfo(userName, "annobot" + (mobile ? "_mob" : ""), itemId,
                    description, timeDiff));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
