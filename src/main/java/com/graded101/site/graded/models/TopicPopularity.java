package com.graded101.site.graded.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topic_popularity_99")
public class TopicPopularity extends BaseGradeEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer topicId;
    private long numberOfUsages;
    private boolean isTopRated;
}
