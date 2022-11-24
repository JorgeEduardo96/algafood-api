package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.controller.core.validation.SegurancaSenha;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static com.algaworks.algafood.api.controller.core.validation.SegurancaSenha.RequisitosSenha.*;

@Getter
@Setter
public class SenhaInput {

    @NotBlank
    private String senhaAtual;

    @SegurancaSenha(requisitos = {MINUSCULO, MAIUSCULO, DIGITO, CARACTER_ESPECIAL})
    @NotBlank
    private String senhaNova;

}
