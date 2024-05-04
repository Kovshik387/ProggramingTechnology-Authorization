package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String errorMessage;
}
