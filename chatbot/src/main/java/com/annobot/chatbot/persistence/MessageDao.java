package com.annobot.chatbot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Long> {

    @Query(value = "select * from chatbot.message where conversation_id like :userId || '_%' " +
            "and content is not null and item_id is not null;", nativeQuery = true)
    List<MessageEntity> getAnnotationByUserId(@Param("userId") String userId);
}
