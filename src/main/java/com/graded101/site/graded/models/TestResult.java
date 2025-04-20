package com.graded101.site.graded.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "test_result_99")
public class TestResult extends BaseGradeEntity {
    @Id
    @GeneratedValue
    private Integer id;
    // TODO
    private Test test;
    private String candidateName;
    private Integer noOfTotalQuestions;
    private Integer noOfCorrectAnswers;
    @Column(updatable = false, nullable = false)
    private LocalDateTime testDateTime;
}
