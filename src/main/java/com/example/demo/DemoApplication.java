package com.example.demo;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Validated
@RestController
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping(path = "/{id}")
    public String findById(final @PathVariable(name = "id") @Id String id) {
        return id;
    }

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, PARAMETER, TYPE_USE})
    @Constraint(validatedBy = IdValidator.class)
    @interface Id {
        String message() default "Id has wrong format, Id should be an hexadecimal string like '6032eb695ed9af5df0ac68e0'";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    static class IdValidator implements ConstraintValidator<Id, String> {

        @Override
        public void initialize(final Id constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext context) {
            return value != null && !value.equals("");
        }
    }

}
