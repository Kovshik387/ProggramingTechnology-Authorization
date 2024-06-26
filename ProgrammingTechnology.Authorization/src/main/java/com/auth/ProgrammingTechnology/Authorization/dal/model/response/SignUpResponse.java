package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class SignUpResponse<TData> {
    private UUID id;
    private int role;
    private String accessToken;
    private String refreshToken;
    private List<String> errorMessage;
    private TData userData;
}
