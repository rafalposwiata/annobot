select * from statistics.stats_info where user_name = '627';

select al.user_name, count(system_name) from
(select system_name, user_name, (max(create_date) - min(create_date)) as d, count(*) from statistics.stats_info
where item_id <> '-' and item_id is not null and user_name in ('107', '218', '329', '409', '627', '518', '733', '831', '148', '247')
group by system_name, user_name) al group by al.user_name;

select stats.system_name, sum(stats.seconds) / 10 as time from (
select system_name, user_name,
       (60 * DATE_PART('mins', max(create_date) - min(create_date)) + DATE_PART('secs', max(create_date) - min(create_date))) as seconds from statistics.stats_info
where item_id <> '-' and item_id is not null and user_name in ('107', '218', '329', '409', '627', '518', '733', '831', '148', '247')
group by system_name, user_name) stats group by stats.system_name order by time;