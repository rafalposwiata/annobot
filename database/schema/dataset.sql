CREATE SCHEMA dataset;

CREATE TABLE dataset.dataset_info (
    dataset_id serial NOT NULL PRIMARY KEY,
    name character varying(200) NOT NULL
);

CREATE TABLE dataset.dataset_item (
    dataset_item_id serial NOT NULL PRIMARY KEY,
    dataset_id BIGINT NOT NULL,
    content TEXT
);

CREATE TABLE dataset.dataset_item_annotation (
    dataset_item_annotation_id serial NOT NULL PRIMARY KEY,
    dataset_id BIGINT NOT NULL,
    dataset_item_id BIGINT NOT NULL,
    conversation_id character varying(200),
    user_input_type character varying(100),
    user_input TEXT
);