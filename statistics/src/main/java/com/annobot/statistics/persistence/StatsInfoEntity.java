package com.annobot.statistics.persistence;

import com.annobot.statistics.model.StatsInfo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.lang.Float.parseFloat;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

@Entity(name = "stats_info")
@Table(schema = "statistics")
public class StatsInfoEntity {

    private final static String STATS_INFO_SEQ = "statistics.stats_info_stats_info_id_seq";

    @Id
    @SequenceGenerator(name = STATS_INFO_SEQ, sequenceName = STATS_INFO_SEQ, allocationSize = 1)
    @GeneratedValue(generator = STATS_INFO_SEQ)
    @Column(name = "stats_info_id")
    private Long statsInfoId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "system_name")
    private String systemName;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "description")
    private String description;

    @Column(name = "time_diff")
    private float timeDiff;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public StatsInfoEntity(StatsInfo statsInfo) {
        this.userName = statsInfo.getUserName();
        this.systemName = statsInfo.getSystemName();
        this.itemId = statsInfo.getItemId();
        this.description = statsInfo.getDescription();
        if (isNoneBlank(statsInfo.getTimeDiff()))
            this.timeDiff = parseFloat(statsInfo.getTimeDiff());
        this.createDate = LocalDateTime.now();
    }
}