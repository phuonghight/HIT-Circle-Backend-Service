package com.example.backendservice.validator.annotation;

import com.example.backendservice.validator.ReactionValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {ReactionValidator.class})
public @interface ValidReaction {

    String message() default "invalid.reaction-format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
