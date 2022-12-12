package com.algaworks.algafood.api.controller.core.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { SegurancaSenha.SegurancaSenhaValidator.class })
public @interface SegurancaSenha {


    String message() default "A senha necessita conter ao menos um caractere da seguinte forma: :requisitos";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class SegurancaSenhaValidator implements ConstraintValidator<SegurancaSenha, String> {

        @Value("#{'${algafood.senha.requisitos}'.split(',')}")
        private List<RequisitosSenha> requisitosParaValidar;

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            char[] caracteres = value.toCharArray();

            boolean temDigito = false;
            boolean temMinusculo = false;
            boolean temMaisculo = false;
            boolean temCaracterEspecial = false;
            boolean maiorQue8Caracteres = value.length() > 8;

            for (int i = 0; i < value.length(); i++) {
                if (!temDigito) {
                    temDigito = Character.isDigit(caracteres[i]);
                }
                if (!temMaisculo) {
                    temMaisculo = Character.isUpperCase(caracteres[i]);
                }
                if (!temMinusculo) {
                    temMinusculo = Character.isLowerCase(caracteres[i]);
                }
                if (!temCaracterEspecial) {
                    temCaracterEspecial = !Character.isDigit(caracteres[i])
                            && !Character.isLetter(caracteres[i])
                            && !Character.isWhitespace(caracteres[i]);
                }
            }

            for (RequisitosSenha requisito : requisitosParaValidar) {
                if (requisito.equals(RequisitosSenha.MAIUSCULO) && !temMaisculo) {
                    return false;
                } else if (requisito.equals(RequisitosSenha.MINUSCULO) && !temMinusculo) {
                    return false;
                } else if (requisito.equals(RequisitosSenha.CARACTERE_ESPECIAL) && !temCaracterEspecial) {
                    return false;
                } else if (requisito.equals(RequisitosSenha.DIGITO) && !temDigito) {
                    return false;
                } else if (requisito.equals(RequisitosSenha.MAIOR_8_DIGITOS) && !maiorQue8Caracteres) {
                    return false;
                }
            }

            return true;
        }
    }

    enum RequisitosSenha {
        MAIUSCULO("Maiúsculo"),
        MINUSCULO("Minúsculo"),
        CARACTERE_ESPECIAL("Caractere Especial"),
        DIGITO("Dígito"),
        MAIOR_8_DIGITOS("Maior que 8 caracteres");

        private String descricao;

        public String getDescricao() {
            return descricao;
        }

        RequisitosSenha(String descricao) {
            this.descricao = descricao;
        }
    }
}
