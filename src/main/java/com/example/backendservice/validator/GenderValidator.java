package com.example.backendservice.validator;

import com.example.backendservice.constant.GenderConstant;
import com.example.backendservice.validator.annotation.ValidGender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String target, ConstraintValidatorContext constraintValidatorContext) {
        return GenderConstant.isValid(target);
    }
}
