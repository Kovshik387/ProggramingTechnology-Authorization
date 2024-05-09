package com.auth.ProgrammingTechnology.Authorization.dal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "auth_accounts")
public class AuthAccount {
    @Id
    private UUID id;
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private String password_hash;
    @Column(name = "code")
    private String code;
    @Column(name = "confirm_code")
    private String confirmCode;
    @Column (name = "confirm")
    private Boolean confirm;
    @Column(name = "role")
    private int role;
    @Column(name = "refresh_token")
    private String refreshToken;
}
