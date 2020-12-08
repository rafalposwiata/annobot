CREATE SCHEMA statistics;

CREATE TABLE statistics.stats_info (
    stats_info_id serial NOT NULL PRIMARY KEY,
    user_name TEXT,
    system_name TEXT,
    item_id TEXT,
    description TEXT,
    time_diff real,
    create_date timestamp without time zone NOT NULL,
    unique (user_name, system_name, item_id, description)
);