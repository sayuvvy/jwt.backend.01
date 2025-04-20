package com.graded101.site.graded.repository;

import com.graded101.site.graded.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
