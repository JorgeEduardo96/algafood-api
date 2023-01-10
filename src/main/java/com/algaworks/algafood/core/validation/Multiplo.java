package com.algaworks.algafood.core.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {Multiplo.MultiploValidator.class })
public @interface Multiplo {

    String message() default "Múltiplo inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int numero();

    class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

        private int numeroMultiplo;

        @Override
        public void initialize(Multiplo constraintAnnotation) {
            this.numeroMultiplo = constraintAnnotation.numero();
        }

        @Override
        public boolean isValid(Number value, ConstraintValidatorContext context) {
            boolean valido = true;

            if (value != null) {
                var valorDecimal = BigDecimal.valueOf(value.doubleValue());
                var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
                var resto = valorDecimal.remainder(multiploDecimal);

                valido = BigDecimal.ZERO.compareTo(resto) == 0;
            }

            return valido;
        }
    }

}
