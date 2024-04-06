package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class SignUpResponse<TData> {
    private String id;
    private int role;
    private String accessToken;
    private String refreshToken;
    private String errorMessage;
    private TData userData;
}
