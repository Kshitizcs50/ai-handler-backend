package com.ai.SpringAiDemo.Repository;

import com.ai.SpringAiDemo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}

