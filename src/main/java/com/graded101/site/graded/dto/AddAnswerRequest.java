package com.graded101.site.graded.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddAnswerRequest {

    @NotEmpty(message="Answer is mandatory")
    @NotNull(message="Answer is mandatory")
    private String answerDesc;
    private String answerFurtherDetail;
    private boolean isCorrect;
    private Integer questionId;

}
