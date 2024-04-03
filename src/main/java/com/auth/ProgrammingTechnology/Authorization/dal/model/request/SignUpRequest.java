package com.auth.ProgrammingTechnology.Authorization.dal.model.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String roleType;
}
