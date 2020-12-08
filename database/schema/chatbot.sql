CREATE SCHEMA chatbot;

CREATE TABLE chatbot.message_template (
    message_template_id serial NOT NULL PRIMARY KEY,
    message_type character varying(100) NOT NULL,
    content TEXT NOT NULL
);

CREATE TABLE chatbot.message (
    message_id serial NOT NULL PRIMARY KEY,
    message_type character varying(100) NOT NULL,
    content TEXT,
    item_id character varying(200),
    conversation_id character varying(200),
    create_date timestamp without time zone NOT NULL
);

CREATE TABLE chatbot.config (
    config_id serial NOT NULL PRIMARY KEY,
    bot_name TEXT unique,
    configuration TEXT
);

ALTER TABLE chatbot.message ADD bot_name TEXT;
ALTER TABLE chatbot.message ALTER COLUMN message_type TYPE TEXT;