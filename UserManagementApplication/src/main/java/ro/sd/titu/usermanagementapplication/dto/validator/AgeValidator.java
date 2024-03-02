package ro.sd.titu.usermanagementapplication.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ro.sd.titu.usermanagementapplication.dto.validator.annotation.AgeLimit;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class AgeValidator implements ConstraintValidator<AgeLimit, String> {

    private int ageLimit = 18;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.ageLimit = constraintAnnotation.limit();
    }

    @Override
    public boolean isValid(String inputDob, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Period.between(LocalDate.parse(inputDob, formatter), LocalDate.now()).getYears() >= ageLimit;
    }
}