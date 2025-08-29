package com.ai.SpringAiDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.SpringAiDemo.jwt.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

	
}
