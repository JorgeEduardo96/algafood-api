package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.controller.core.validation.SegurancaSenha;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @SegurancaSenha
    @NotBlank
    private String senha;
    
}
