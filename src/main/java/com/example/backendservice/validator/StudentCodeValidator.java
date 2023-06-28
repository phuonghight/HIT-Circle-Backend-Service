package com.example.backendservice.validator;

import com.example.backendservice.validator.annotation.ValidStudentCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StudentCodeValidator implements ConstraintValidator<ValidStudentCode, String> {
    private Pattern pattern;

    @Override
    public void initialize(ValidStudentCode validStudentCode) {
        String regex = validStudentCode.regexp();
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String studentCode, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(studentCode).matches();
    }
}
