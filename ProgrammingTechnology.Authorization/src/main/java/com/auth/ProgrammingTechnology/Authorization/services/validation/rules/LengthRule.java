package com.auth.ProgrammingTechnology.Authorization.services.validation.rules;

import com.auth.ProgrammingTechnology.Authorization.services.validation.ValidationRule;

import java.util.List;

public class LengthRule extends ValidationRule {
    private final int min;
    private final int max;
    private final String type;
    public LengthRule(int min, int max,String type){
        this.min = min; this.max = max; this.type = type;
    }

    @Override
    public boolean check(String value, List<String> errorMessage) {
        if (!(value.length() <= this.max && value.length() >= this.min)){
            errorMessage.add("Длина "+ this.type +" должна быть от " + this.min + " до " + this.max + " символов") ;
            return false;
        }
        return checkNext(value,errorMessage);
    }
}
