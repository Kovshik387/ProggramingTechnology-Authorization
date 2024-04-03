package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.*;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.repository.AccountDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class AccountService {
    @Autowired
    private final AccountDao context;
    @Autowired
    private final TokenManager tokenManager;
    public SignInResponse getByEmail(@NonNull SignInRequest model) throws Exception {
        var account = context.getByLogin(model);
        if (account == null) return SignInResponse.builder().build();

        final String accessToken = tokenManager.generateJwtToken(account);
        final String refreshToken = tokenManager.generateRefreshToken(account);

        return SignInResponse.builder()
                .accessToken(accessToken).
                refreshToken(refreshToken).
                id(account.getId()).
                build();
        //return Optional.of(account);
    }

    public SignUpResponse signUp(@NonNull SignUpRequest request) throws SQLException {

        var account = Account.builder().
                email(request.getEmail()).
                name(request.getName()).
                passwordHash(request.getPassword()).
                roleType(request.getRoleType()).
                refreshToken("fdsfdsfds").
                build();

        var exist = context.accountExist(request.getEmail());

        if (exist != 0) return null;

        context.add(account);
        var id = context.accountExist(request.getEmail());

        final String accessToken = tokenManager.generateJwtToken(account);
        final String refreshToken = tokenManager.generateRefreshToken(account);
        return SignUpResponse.builder()
                .accessToken(accessToken).
                refreshToken(refreshToken).
                id(id).
                build();
    }
}
