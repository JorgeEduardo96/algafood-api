package com.algaworks.algafood.api.controller.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentType.FileContentTypeValidator.class })
public @interface FileContentType {

    String message() default "Formato do arquivo inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] allowed();

    class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

        private List<String> allowedsContentTypes;

        @Override
        public void initialize(FileContentType constraintAnnotation) {
            this.allowedsContentTypes = List.of(constraintAnnotation.allowed());
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            return value == null || allowedsContentTypes.contains(value.getContentType());
        }


    }


}
