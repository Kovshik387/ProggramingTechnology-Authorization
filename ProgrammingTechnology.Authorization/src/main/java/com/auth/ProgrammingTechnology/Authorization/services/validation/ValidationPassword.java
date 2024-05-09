package com.auth.ProgrammingTechnology.Authorization.services.validation;

import com.auth.ProgrammingTechnology.Authorization.services.validation.rules.DigitRule;
import com.auth.ProgrammingTechnology.Authorization.services.validation.rules.LengthRule;
import com.auth.ProgrammingTechnology.Authorization.services.validation.rules.PrefixRule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Setter
@Getter
public class ValidationPassword {
    private String value;
    private List<String> errorMessage;
    public void checkValidationRules(){
        ValidationRule.link(
                new LengthRule(5, 20,"пароля"),
                new DigitRule()
        ).check(value, errorMessage);
    }
}
