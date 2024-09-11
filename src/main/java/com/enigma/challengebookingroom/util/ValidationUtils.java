package com.enigma.challengebookingroom.util;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtils {

    private final Validator validator;

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
    }
}