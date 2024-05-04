package com.controller;

import com.auth.ProgrammingTechnology.Authorization.services.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@AllArgsConstructor
@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private AuthServiceImpl authService;

}
