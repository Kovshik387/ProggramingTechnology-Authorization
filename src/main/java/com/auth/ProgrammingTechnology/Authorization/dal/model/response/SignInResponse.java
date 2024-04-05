package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class SignInResponse {
    private int id;
    private int role;
    private String accessToken;
    private String refreshToken;
    private String errorMessage;
}
