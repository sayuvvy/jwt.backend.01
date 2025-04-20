package com.graded101.site.graded.repository;

import com.graded101.site.graded.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
