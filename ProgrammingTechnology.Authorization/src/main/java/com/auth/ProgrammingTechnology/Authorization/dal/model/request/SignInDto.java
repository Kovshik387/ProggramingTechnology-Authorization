package com.auth.ProgrammingTechnology.Authorization.dal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInDto {
    private String email;
    private String password;
}
