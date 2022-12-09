package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.controller.core.validation.SegurancaSenha;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    @SegurancaSenha
    private String senhaNova;

}
