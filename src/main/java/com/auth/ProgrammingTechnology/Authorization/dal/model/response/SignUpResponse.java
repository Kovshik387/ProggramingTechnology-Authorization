package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;

@Builder
public class SignUpResponse {
    private int id;
    private String accessToken;
    private String refreshToken;
}
