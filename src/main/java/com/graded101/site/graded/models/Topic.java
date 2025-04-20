package com.graded101.site.graded.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topic_99")
public class Topic extends BaseGradeEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique=true)
    private String topicCode;
    private String topicName;
    private String topicDescription;
    private long numberOfTotalQuestions;
    private long numberOfRelevantQuestions;

    @OneToMany(mappedBy = "topic")
    List<Question> questions;
}
