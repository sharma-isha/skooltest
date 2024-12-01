package com.skooltest.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skooltest.entities.tables.assessment.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
