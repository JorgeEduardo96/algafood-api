package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioSemSenhaInput {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

}
