package com.auth.ProgrammingTechnology.Authorization.controller;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignUpDto;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignInResponse;
import com.auth.ProgrammingTechnology.Authorization.dal.model.response.SignUpResponse;
import com.auth.ProgrammingTechnology.Authorization.services.AuthServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
public class AuthControllerTest {

    @Mock
    private AuthServiceImpl authorizationService;

    @InjectMocks
    private AuthController authController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUp() throws Exception {
        SignUpDto signUpDto = SignUpDto.builder().
                surname("test").
                firstName("test").
                lastName("test").
                role(1).
                email("test@mail.ru").
                password("passw00rd").
                build();

        SignUpResponse<Account> signUpResponse = SignUpResponse.<Account>builder().build();

        when(authorizationService.signUp(any(SignUpDto.class))).thenReturn(signUpResponse);

        ResponseEntity<SignUpResponse<Account>> responseEntity = authController.signUp(signUpDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(signUpResponse, responseEntity.getBody());
    }

    @Test
    public void testSignIn() throws Exception {
        SignInDto signInDto = SignInDto.builder().
                email("test@mail.ru").
                password("passw00rd").
                build();

        SignInResponse signInResponse = SignInResponse.builder().build();

        when(authorizationService.signIn(any(SignInDto.class))).thenReturn(signInResponse);

        ResponseEntity<SignInResponse> responseEntity = authController.signIn(signInDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(signInResponse, responseEntity.getBody());
    }

    @Test
    public void testForgetPassword() throws Exception {
        String email = "test@example.com";

        when(authorizationService.forgetPassword(email)).thenReturn("Success");

        ResponseEntity<String> responseEntity = authController.forgetPassword(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
    }

    @Test
    public void testNewPassword() throws Exception {
        String code = "123456";
        String password = "newPassword";
        String confirmPassword = "newPassword";

        when(authorizationService.newPassword(code, password, confirmPassword)).thenReturn("Success");

        ResponseEntity<String> responseEntity = authController.newPassword(code, password, confirmPassword);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
    }

    @Test
    public void testConfirmAccount() throws Exception {
        String code = "123456";

        when(authorizationService.confirmPassword(code)).thenReturn("Success");

        ResponseEntity<String> responseEntity = authController.confirmAccount(code);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
    }
}