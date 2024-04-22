package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountResponse {
    private String id;
    private int role;
}
