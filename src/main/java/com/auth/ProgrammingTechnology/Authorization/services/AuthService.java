package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.*;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.repository.AccountRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TokenManager tokenManager;
    private final String hash;
    public AuthService(AccountRepository accountRepository, TokenManager tokenManager, @Value("${password.hash}") int salt) {
        this.accountRepository = accountRepository;
        this.tokenManager = tokenManager;
        this.hash = BCrypt.gensalt(salt);
    }
    public SignInResponse signIn(@NonNull SignInRequest request) throws Exception {
        var account = accountRepository.findByEmailAddress(request.getEmail());

        if (account == null) return SignInResponse.builder().errorMessage("Пользователь не найден").build();

        if(!BCrypt.checkpw(request.getPassword(),account.getPassword_hash())){
            return SignInResponse.builder().errorMessage("Неверный пароль").build();
        }

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
        var email = claims.getSubject();
        var account = accountRepository.findByEmailAddress(email);
        return AccountResponse.builder().
                id(account.getId()).
                role(account.getRole()).
                build();
    }

    public SignUpResponse<Account> signUp(@NonNull SignUpRequest request) throws SQLException {
        var account = Account.builder().
                id(UUID.randomUUID()).
                firstName(request.getFirstName()).
                surname(request.getSurname()).
                lastName(request.getLastName()).
                email(request.getEmail()).
                passwordHash(BCrypt.hashpw(request.getPassword(),this.hash)).
                role(request.getRole()).
                activityStatus(0).
                currentEvent(0).
                createdAt(new Timestamp(new Date().getTime())).
                updatedAt(new Timestamp(new Date().getTime())).
                build();

        var exist = accountRepository.findByEmailAddress(request.getEmail());

        if (exist != null) return SignUpResponse.<Account>builder().errorMessage("Пользователь с данной почтой уже существует").build();

        var authAccount = AuthAccount.builder().
                id(account.getId().toString()).
                role(account.getRole()).
                email(account.getEmail()).
                password_hash(account.getPasswordHash()).
                build();

        accountRepository.save(authAccount);

        final String accessToken = tokenManager.generateJwtToken(authAccount);
        final String refreshToken = tokenManager.generateRefreshToken(authAccount);
        return SignUpResponse.<Account>builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                role(request.getRole()).
                id(authAccount.getId()).
                userData(account).
                build();
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws SQLException {
        if (tokenManager.validateRefreshToken(refreshToken)) {
            var claims = tokenManager.getRefreshClaims(refreshToken);
            var email = claims.getSubject();
            if (tokenManager.validateRefreshToken(refreshToken)) {
                var account = accountRepository.findByEmailAddress(email);
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
