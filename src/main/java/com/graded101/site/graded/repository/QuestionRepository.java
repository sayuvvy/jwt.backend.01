package com.graded101.site.graded.repository;

import com.graded101.site.graded.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
