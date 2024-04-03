package com.auth.ProgrammingTechnology.Authorization.controller;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpRequest;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.repository.AccountDao;
import com.auth.ProgrammingTechnology.Authorization.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
public class AuthController {
    @Autowired
    private final AuthorizationService authorizationService;
    @PostMapping("SignUp")
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest request) throws SQLException {
        return ResponseEntity.ok(authorizationService.signUp(request));
    }
    @GetMapping("SignIn")
    public ResponseEntity<SignInResponse> signIn(SignInRequest request) throws Exception {
        return ResponseEntity.ok(authorizationService.signIn(request));
    }
    @PostMapping
    public ResponseEntity<String> getNewRefreshToken(@RequestBody String request){
        return ResponseEntity.ok("");
    }

    @GetMapping("GetInfo")
    public ResponseEntity<String> getInfo(){
        return ResponseEntity.ok("Авторизирован");
    }
}
