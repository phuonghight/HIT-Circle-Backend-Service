package com.example.backendservice.validator.annotation;

import com.example.backendservice.validator.StudentCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {StudentCodeValidator.class})
public @interface ValidStudentCode {

    String message() default "invalid.student-code-format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default ".*";
}
