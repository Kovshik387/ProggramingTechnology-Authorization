package com.auth.ProgrammingTechnology.Authorization.dal.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountResponse {
    private int id;
    private int role;
}
