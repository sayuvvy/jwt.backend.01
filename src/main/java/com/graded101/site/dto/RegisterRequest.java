package com.graded101.site.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotEmpty(message="Firstname is mandatory")
    @NotNull(message="Firstname is mandatory")
    private String firstname;
    @NotEmpty(message="Lastname is mandatory")
    @NotNull(message="Lastname is mandatory")
    private String lastname;
    @NotEmpty(message="Email is mandatory")
    @NotNull(message="Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message="Password is mandatory")
    @NotNull(message="Password is mandatory")
    @Size(min=8, message = "Password to be 8 char long ")
    private String password;
}
