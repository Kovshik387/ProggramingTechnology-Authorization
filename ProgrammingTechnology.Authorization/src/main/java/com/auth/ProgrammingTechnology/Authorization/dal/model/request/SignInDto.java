package com.auth.ProgrammingTechnology.Authorization.dal.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignInDto {
    private String email;
    private String password;
}
