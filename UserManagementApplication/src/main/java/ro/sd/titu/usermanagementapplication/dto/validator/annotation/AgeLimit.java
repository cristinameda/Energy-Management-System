package ro.sd.titu.usermanagementapplication.dto.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ro.sd.titu.usermanagementapplication.dto.validator.AgeValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AgeValidator.class})
public @interface AgeLimit {

    int limit() default 120;

    String message() default "Age should be at least 18 years old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
