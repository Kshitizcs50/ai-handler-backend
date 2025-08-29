package com.ai.SpringAiDemo.Repository;

import com.ai.SpringAiDemo.Hero;


import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

	}

