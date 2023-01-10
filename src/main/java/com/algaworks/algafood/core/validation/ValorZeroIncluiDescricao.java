package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricao.ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {

    String message() default "descrição obrigatória inválida.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valorField();

    String descricaoField();

    String descricaoObrigatoria();


    class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

        private String valorField;
        private String descricaoField;
        private String descricaoObrigatoria;

        @Override
        public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
            this.valorField = constraintAnnotation.valorField();
            this.descricaoField = constraintAnnotation.descricaoField();
            this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
        }

        @Override
        public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
            boolean valido = true;

            try {
                BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField)
                        .getReadMethod().invoke(objetoValidacao);

                String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
                        .getReadMethod().invoke(objetoValidacao);

                if ( valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                    valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
                }

            } catch (Exception e) {
                throw new ValidationException(e);
            }

            return valido;
        }
    }

}
