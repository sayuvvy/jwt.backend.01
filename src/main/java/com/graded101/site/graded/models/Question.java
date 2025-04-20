package com.graded101.site.graded.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question_99")
public class Question extends BaseGradeEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String desc;
    private boolean isRelevant;
    private boolean isMultipleAnswer;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "question")
    private List<Answer> allAnswers;

}
