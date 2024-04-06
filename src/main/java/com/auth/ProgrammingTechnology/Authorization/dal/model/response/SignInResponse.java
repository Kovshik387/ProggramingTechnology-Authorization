package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Builder
@Data
public class SignInResponse {
    private String id;
    private int role;
    private String accessToken;
    private String refreshToken;
    private String errorMessage;
}
