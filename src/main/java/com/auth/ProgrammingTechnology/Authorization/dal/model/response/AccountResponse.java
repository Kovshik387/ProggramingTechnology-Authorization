package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AccountResponse {
    private String id;
    private int role;
}
