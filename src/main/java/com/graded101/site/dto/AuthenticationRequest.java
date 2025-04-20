package com.graded101.site.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message="Email is mandatory")
    @NotNull(message="Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message="Password is mandatory")
    @NotNull(message="Password is mandatory")
    @Size(min=8, message = "Password to be 8 char long ")
    private String password;
}
