package com.auth.ProgrammingTechnology.Authorization.controller;

import com.auth.ProgrammingTechnology.Authorization.dal.model.response.AccountResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.JwtResponse;
import com.auth.ProgrammingTechnology.Authorization.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.sql.SQLException;

@RestController
@RequestMapping("token/")
@AllArgsConstructor
public class TokenController {
    @Autowired
    private final AuthService authorizationService;
    @GetMapping("GetInfo")
    public ResponseEntity<AccountResponse> getInfo(@NonNull String access) throws SQLException {
        return ResponseEntity.ok(authorizationService.getInfo(access));
    }
    @GetMapping("NewAccess")
    public ResponseEntity<JwtResponse> getNewAccess(@NonNull String refresh) throws SQLException {
        return ResponseEntity.ok(authorizationService.getAccessToken(refresh));
    }
}
