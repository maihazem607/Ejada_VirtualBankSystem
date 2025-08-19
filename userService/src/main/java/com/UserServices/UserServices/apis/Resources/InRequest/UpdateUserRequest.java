package com.UserServices.UserServices.apis.Resources.InRequest;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;


    @Email(message = "Invalid email format")
    private String email;


    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String firstName;

    private String lastName;
}