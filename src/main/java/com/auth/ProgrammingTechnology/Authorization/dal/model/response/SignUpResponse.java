package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpResponse {
    private int id;
    private int role;
    private String accessToken;
    private String refreshToken;
    private String errorMessage;
}