package com.auth.ProgrammingTechnology.Authorization.dal.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    private String firstName;
    private String surname;
    private String lastName;
    private String email;
    private String password;
    private int role;
}
