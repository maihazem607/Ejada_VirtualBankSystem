package com.AccountService.AccountService.applications.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
