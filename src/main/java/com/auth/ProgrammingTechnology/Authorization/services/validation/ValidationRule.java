package com.auth.ProgrammingTechnology.Authorization.services.validation;

import java.util.List;

public abstract class ValidationRule {
    private ValidationRule next;
    public static ValidationRule link(ValidationRule first, ValidationRule... chain) {
        ValidationRule head = first;
        for (ValidationRule nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }
    public abstract boolean check(String password, List<String> ErrorMessage);
    protected boolean checkNext(String password, List<String> ErrorMessage) {
        if (next == null) {
            return true;
        }
        return next.check(password,ErrorMessage);
    }
}
