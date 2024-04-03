package com.auth.ProgrammingTechnology.Authorization.dal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequest {
    private String email;
    private String password;
}
