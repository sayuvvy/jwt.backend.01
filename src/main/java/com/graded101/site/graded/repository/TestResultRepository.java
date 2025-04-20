package com.graded101.site.graded.repository;

import com.graded101.site.graded.models.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
}
