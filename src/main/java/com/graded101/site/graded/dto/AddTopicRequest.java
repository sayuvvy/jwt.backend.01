package com.graded101.site.graded.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddTopicRequest {

    @NotEmpty(message="Topic code is mandatory")
    @NotNull(message="Topic code is mandatory")
    private String topicCode;
    @NotEmpty(message="Topic Name is mandatory")
    @NotNull(message="Topic Name is mandatory")
    private String topicName;
    private String topicDescription;

}
