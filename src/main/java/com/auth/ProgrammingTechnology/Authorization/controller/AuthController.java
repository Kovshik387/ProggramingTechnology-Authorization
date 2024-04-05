package com.auth.ProgrammingTechnology.Authorization.controller;

import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.services.AuthService;
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
    private final AuthService authorizationService;
    @PostMapping("SignUp")
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest request) throws SQLException {
        return ResponseEntity.ok(authorizationService.signUp(request));
    }
    @GetMapping("SignIn")
    public ResponseEntity<SignInResponse> signIn(SignInRequest request) throws Exception {
        return ResponseEntity.ok(authorizationService.signIn(request));
    }
}
