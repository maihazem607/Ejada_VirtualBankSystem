package com.BFFService.BFFService.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
        private UUID userId;
        private String username;
        private String email;
        private String firstName;
        private String lastName;


}
