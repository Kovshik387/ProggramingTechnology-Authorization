package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.*;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.repository.AccountRepository;
import com.auth.ProgrammingTechnology.Authorization.services.Infastructure.AuthService;
import com.auth.ProgrammingTechnology.Authorization.services.validation.ValidationEmail;
import com.auth.ProgrammingTechnology.Authorization.services.validation.ValidationPassword;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TokenManagerImpl tokenManager;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private ValidationEmail validationEmail;
    @Autowired
    private ValidationPassword validationPassword;
    private final String hash;
    public AuthServiceImpl(AccountRepository accountRepository, TokenManagerImpl tokenManager, MessageServiceImpl messageService, ValidationPassword validationPassword, ValidationEmail validationEmail1, @Value("${password.hash}") int salt) {
        this.accountRepository = accountRepository;
        this.tokenManager = tokenManager;
        this.messageService = messageService;
        this.validationEmail = validationEmail1;
        this.validationPassword = validationPassword;
        this.hash = BCrypt.gensalt(salt);
    }
    public SignInResponse signIn(@NonNull SignInDto request) throws Exception {
        var account = accountRepository.findByEmailAddress(request.getEmail());

        if (account == null) return SignInResponse.builder().errorMessage("Пользователь не найден").build();

        if(!BCrypt.checkpw(request.getPassword(),account.getPassword_hash())){
            return SignInResponse.builder().errorMessage("Неверный пароль").build();
        }

        final String accessToken = tokenManager.generateJwtToken(account.getEmail());
        final String refreshToken = tokenManager.generateRefreshToken(account.getEmail());

        account.setRefreshToken(refreshToken); accountRepository.save(account);

        return SignInResponse.builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                id(account.getId()).
                role(account.getRole()).
                build();
    }

    public SignUpResponse<Account> signUp(@NonNull SignUpDto request) throws SQLException {
        var exist = accountRepository.findByEmailAddress(request.getEmail());
        if (exist != null) {
            return SignUpResponse.<Account>builder().errorMessage(List.of("Пользователь с данной почтой уже существует")).build();
        }

        var errors = new ArrayList<String>();
        validationEmail.setValue(request.getEmail()); validationEmail.setErrorMessage(errors); validationEmail.checkValidationRules();
        validationPassword.setValue(request.getPassword()); validationPassword.setErrorMessage(errors); validationPassword.checkValidationRules();

        if (!errors.isEmpty()){
            return SignUpResponse.<Account>builder().errorMessage(errors).build();
        }

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

        final String accessToken = tokenManager.generateJwtToken(account.getEmail());
        final String refreshToken = tokenManager.generateRefreshToken(account.getEmail());

        var authAccount = AuthAccount.builder().
                id(account.getId()).
                role(account.getRole()).
                email(account.getEmail()).
                password_hash(account.getPasswordHash()).
                refreshToken(refreshToken).
                build();

        accountRepository.save(authAccount);

        return SignUpResponse.<Account>builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                role(request.getRole()).
                id(authAccount.getId()).
                userData(account).
                build();
    }

    public String forgetPassword(@NonNull String email){
        var exist = accountRepository.findByEmailAddress(email);

        if (exist == null) return "Пользователя с данной почтой не существует";

        String code = exist.getCode();

        if (exist.getCode() == null){
            code = BCrypt.hashpw(exist.getEmail() + exist.getId() ,this.hash);
            exist.setCode(code);
            accountRepository.save(exist);
        }

        if (!messageService.sendMessage(exist,code)){
             return "Произошла ошибка";
        }

        return "Письмо отправлено";
    }

    public String newPassword(@NonNull String code, String newPassword, String confirmPassword){
        if(!newPassword.equals(confirmPassword)){
            return "Пароли не совпадают";
        }

        List<String> errors = new ArrayList<>();
        validationPassword.setValue(newPassword); validationPassword.setErrorMessage(errors); validationPassword.checkValidationRules();

        var data = code.split("\\.\\.");
        var uuid = UUID.fromString(data[1]);

        var account = accountRepository.findById(uuid);
        if (account.isEmpty()){
            return "Пользователь не найден";
        }
        if (!account.get().getCode().equals(data[0])){
            return "Произошла ошибка";
        }

        account.get().setPassword_hash(BCrypt.hashpw(newPassword,this.hash));
        account.get().setCode(null);
        accountRepository.save(account.get());

        return "Успешно";
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

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws SQLException {
        if (tokenManager.validateRefreshToken(refreshToken)) {
            var claims = tokenManager.getRefreshClaims(refreshToken);
            var email = claims.getSubject();
            if (tokenManager.validateRefreshToken(refreshToken)) {
                var account = accountRepository.findByEmailAddress(email);

                if (!account.getRefreshToken().equals(refreshToken)){
                    return new JwtResponse(null,null,"Токен устарел");
                }

                var accessToken = tokenManager.generateJwtToken(account.getEmail());

                if (tokenManager.checkRefreshExpire(refreshToken)){
                    return new JwtResponse(accessToken,tokenManager.generateRefreshToken(account.getEmail()),null);
                }
                return new JwtResponse(accessToken, refreshToken,"");
            }
        }
        return new JwtResponse(null, null,"Невалидный токен");
    }
}
