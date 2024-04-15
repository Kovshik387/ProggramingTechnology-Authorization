package com.auth.ProgrammingTechnology.Authorization.services.Infastructure;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import lombok.NonNull;

import java.sql.SQLException;

public interface AuthService {
    public SignInResponse signIn(@NonNull SignInRequest request) throws Exception;
    public AccountResponse getInfo(@NonNull String token) throws SQLException;
    public SignUpResponse<Account> signUp(@NonNull SignUpRequest request) throws SQLException;
    public JwtResponse getAccessToken(@NonNull String refreshToken) throws SQLException;
}
