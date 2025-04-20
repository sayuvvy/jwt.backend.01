package com.graded101.site.graded.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddQuestionRequest {

    @NotEmpty(message="Question title is mandatory")
    @NotNull(message="Question title is mandatory")
    private String title;
    @NotEmpty(message="Question Desc/Snippet is mandatory")
    @NotNull(message="Question Desc/Snippet is mandatory")
    private String desc;
    private Integer topicId;
    private boolean isRelevant;
    private boolean isMultipleAnswer;

}
