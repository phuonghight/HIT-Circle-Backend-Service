package com.example.backendservice.validator.annotation;

import com.example.backendservice.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {PhoneValidator.class})
public @interface ValidPhone {

    String message() default "invalid.phone-format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "^(0?)(3[2-9]|5[2|6|8|9]|7[0|6-9]|8[0-9]|9[0-4|6-9])[0-9]{7}$";
}
