package com.algaworks.algafood.api.controller.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileSize.FileSizeValidator.class })
public @interface FileSize {

    String message() default "Tamanho do arquivo inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();

    class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

        private DataSize maxSize;

        @Override
        public void initialize(FileSize constraintAnnotation) {
            this.maxSize = DataSize.parse(constraintAnnotation.max());
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            return value == null || value.getSize() <= this.maxSize.toBytes();
        }
    }

}
