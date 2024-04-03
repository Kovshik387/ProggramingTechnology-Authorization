package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Component
@Builder
public class AuthorizationService {
    @Autowired
    private final AccountService accountService;
    public SignUpResponse signUp(SignUpRequest model) throws SQLException {
        return accountService.signUp(model);
    }

    public SignInResponse signIn(SignInRequest model) throws Exception {
        return accountService.getByEmail(model);
    }

    public SignInResponse getAccessToken(@NonNull String refreshToken){
        return null;
    }

}
