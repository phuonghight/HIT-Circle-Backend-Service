package com.example.backendservice.validator;

import com.example.backendservice.constant.ReactionConstant;
import com.example.backendservice.validator.annotation.ValidReaction;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReactionValidator implements ConstraintValidator<ValidReaction, String> {
    @Override
    public boolean isValid(String target, ConstraintValidatorContext constraintValidatorContext) {
        if (target == null) {
            return false;
        }
        return ReactionConstant.isValid(target);
    }
}
