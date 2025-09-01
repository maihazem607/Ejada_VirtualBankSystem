package com.UserServices.UserServices.applications.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name=  "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="userId")
    private String Id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;
}
