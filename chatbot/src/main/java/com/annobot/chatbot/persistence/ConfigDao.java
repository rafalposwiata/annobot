package com.annobot.chatbot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConfigDao extends JpaRepository<ConfigEntity, Long> {

    ConfigEntity findByBotName(String botName);

    @Query(value = "select bot_name from chatbot.config", nativeQuery = true)
    List<String> getBotNames();
}
