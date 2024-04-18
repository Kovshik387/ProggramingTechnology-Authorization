package com.auth.ProgrammingTechnology.Authorization.services.validation.rules;

import com.auth.ProgrammingTechnology.Authorization.services.validation.ValidationRule;

import java.util.List;

public class LengthRule extends ValidationRule {
    private final int min;
    private final int max;
    public LengthRule(int min, int max){
        this.min = min; this.max = max;
    }

    @Override
    public boolean check(String value, List<String> errorMessage) {
        if (!(value.length() <= max && value.length() >= min)){
            errorMessage.add("Длина почты должна быть от " + min + " до " + max + " символов") ;
            return false;
        }
        return checkNext(value,errorMessage);
    }
}
