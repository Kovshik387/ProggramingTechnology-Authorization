package com.auth.ProgrammingTechnology.Authorization.services.Infastructure;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import lombok.NonNull;

import java.sql.SQLException;

public interface AuthService {
    public SignInResponse signIn(@NonNull SignInDto request) throws Exception;
    public AccountResponse getInfo(@NonNull String token) throws SQLException;
    public SignUpResponse<Account> signUp(@NonNull SignUpDto request) throws SQLException;
    public JwtResponse getAccessToken(@NonNull String refreshToken) throws SQLException;
}
