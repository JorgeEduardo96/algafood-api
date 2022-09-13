package com.algaworks.algafood.api.controller.core.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyUpperCaseValidator implements ConstraintValidator<OnlyUpperCase, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isAllUpperCase(value.replaceAll(" ", ""));
    }
}
