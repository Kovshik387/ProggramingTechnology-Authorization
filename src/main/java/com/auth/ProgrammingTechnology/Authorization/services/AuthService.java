package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.*;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.repository.AccountDao;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class AuthService {
    @Autowired
    private final AccountDao context;
    @Autowired
    private final TokenManager tokenManager;
    public SignInResponse signIn(@NonNull SignInRequest request) throws Exception {
        var account = context.getByCredits(request);
        if (account == null) return SignInResponse.builder().errorMessage("Пользователь не найден").build();

        final String accessToken = tokenManager.generateJwtToken(account);
        final String refreshToken = tokenManager.generateRefreshToken(account);

        return SignInResponse.builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                id(account.getId()).
                role(account.getRole()).
                build();
    }

    public AccountResponse getInfo(@NonNull String token) throws SQLException {
        var claims = tokenManager.getAccessClaims(token);
        var login = claims.getSubject();
        var account = context.getByLogin(login);
        return AccountResponse.builder().id(account.getId()).role(account.getRole()).build();
    }

    public SignUpResponse signUp(@NonNull SignUpRequest request) throws SQLException {

        var account = Account.builder().
                firstName(request.getFirstName()).
                surname(request.getSurname()).
                lastName(request.getLastName()).
                email(request.getEmail()).
                passwordHash(request.getPassword()).
                role(request.getRole()).
                build();

        var exist = context.accountExist(request.getEmail());

        if (exist != 0) return SignUpResponse.builder().errorMessage("Пользователь с данной почтой уже существует").build();

        context.add(account); var id = context.accountExist(request.getEmail());

        final String accessToken = tokenManager.generateJwtToken(account);
        final String refreshToken = tokenManager.generateRefreshToken(account);
        return SignUpResponse.builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                role(request.getRole()).
                id(id).
                build();
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws SQLException {
        if (tokenManager.validateRefreshToken(refreshToken)) {
            var claims = tokenManager.getRefreshClaims(refreshToken);
            var login = claims.getSubject();
            if (tokenManager.validateRefreshToken(refreshToken)) {
                var account = context.getByLogin(login);
                var accessToken = tokenManager.generateJwtToken(account);

                if (tokenManager.checkRefreshExpire(refreshToken)){
                    return new JwtResponse(accessToken,tokenManager.generateRefreshToken(account));
                }
                return new JwtResponse(accessToken, refreshToken);
            }
        }
        return new JwtResponse(null, null);
    }
}
