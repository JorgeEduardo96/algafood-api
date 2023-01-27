package com.algaworks.algafood.core.validation;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {OnlyUpperCase.OnlyUpperCaseValidator.class })
public @interface OnlyUpperCase {

    String message() default "Apenas caracteres maiúsculos.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

   class OnlyUpperCaseValidator implements ConstraintValidator<OnlyUpperCase, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isAllUpperCase(value.replaceAll(" ", ""));
        }
    }

}
