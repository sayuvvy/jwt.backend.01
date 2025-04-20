package com.graded101.site.graded.models;


import com.graded101.site.model.User;
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
@Table(name = "test_99")
public class Test extends BaseGradeEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String authorName;
    // TODO
    private Topic topic;
    private TestLevel testLevel;
    @ManyToMany
    private List<Question> testQuestions;
}
