package com.auth.ProgrammingTechnology.Authorization.dal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
//@Table(name = "accounts")
@Builder
public class Account {
//    @Column(name = "account_id")
    private int id;
//    @Column(name = "name")
    private String name;
//    @Column(name = "email")
    private String email;
//    @Column(name = "password_hash")
    private String passwordHash;
//    @Column(name = "refresh_token")
    private String refreshToken;
//    @Column(name = "role_type")
    private String roleType;
}
