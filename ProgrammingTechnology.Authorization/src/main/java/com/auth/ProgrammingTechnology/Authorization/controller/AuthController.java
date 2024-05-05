package com.auth.ProgrammingTechnology.Authorization.controller;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.services.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
public class AuthController {
    @Autowired
    private final AuthServiceImpl authorizationService;
    @PostMapping("SignUp")
    public ResponseEntity<SignUpResponse<Account>> signUp(SignUpDto request) throws SQLException {
        var result = authorizationService.signUp(request);
        if(result.getErrorMessage() != null){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("SignIn")
    public ResponseEntity<SignInResponse> signIn(SignInDto request) throws Exception {
        var result = authorizationService.signIn(request);
        if(result.getErrorMessage() != null){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("ForgetPassword")
    public ResponseEntity<String> forgetPassword(String email){
        return ResponseEntity.ok(authorizationService.forgetPassword(email));
    }
    @PostMapping("NewPassword")
    public ResponseEntity<String> newPassword(String code,String password,String confirmPassword){
        return ResponseEntity.ok(authorizationService.newPassword(code,password,confirmPassword));
    }
}
