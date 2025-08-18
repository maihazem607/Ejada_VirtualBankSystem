package com.UserServices.UserServices.apis.Resources.InRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;

}
