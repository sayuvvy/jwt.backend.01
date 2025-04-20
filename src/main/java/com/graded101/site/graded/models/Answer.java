package com.graded101.site.graded.models;

import jakarta.persistence.*;
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
@Table(name = "answer_99")
public class Answer extends BaseGradeEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String answerDesc;
    private String answerFurtherDetail;
    private boolean isCorrect;
    private boolean isCandidateSelection;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;
}