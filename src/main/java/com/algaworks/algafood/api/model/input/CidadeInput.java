package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.controller.core.validation.OnlyUpperCase;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @NotBlank
    @OnlyUpperCase
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estado;

}
