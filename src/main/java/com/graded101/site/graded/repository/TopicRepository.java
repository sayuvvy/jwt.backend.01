package com.graded101.site.graded.repository;

import com.graded101.site.graded.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
