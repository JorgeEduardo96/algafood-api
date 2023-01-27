package com.algaworks.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
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
