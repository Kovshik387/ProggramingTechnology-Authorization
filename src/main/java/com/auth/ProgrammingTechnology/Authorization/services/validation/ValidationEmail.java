package com.auth.ProgrammingTechnology.Authorization.services.validation;

import com.auth.ProgrammingTechnology.Authorization.services.validation.rules.LengthRule;
import com.auth.ProgrammingTechnology.Authorization.services.validation.rules.PrefixRule;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
public class ValidationEmail {
    private String value;
    private List<String> errorMessage;
    public void checkValidationRules(){
        ValidationRule.link(
                new LengthRule(5, 50,"почты"),
                new PrefixRule()
        ).check(value, errorMessage);
    }
}
