package com.auth.ProgrammingTechnology.Authorization.services.validation.rules;

import com.auth.ProgrammingTechnology.Authorization.services.validation.ValidationRule;

import java.util.List;

public class DigitRule extends ValidationRule {
    @Override
    public boolean check(String password, List<String> ErrorMessage) {
        return checkDigit(password,ErrorMessage);
    }
    private boolean checkDigit(String password,List<String> ErrorMessage){
        int count = 0;
        for (int i = 0; i < password.length();i++) {
            if (Character.isDigit(password.charAt(i)))
                return true;
        }
        ErrorMessage.add("Пароль должен содержать хотя бы одну цифру");
        return false;
    }
}
