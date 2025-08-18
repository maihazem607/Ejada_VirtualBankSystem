package com.UserServices.UserServices.apis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
