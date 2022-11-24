package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.controller.core.validation.SegurancaSenha;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.algaworks.algafood.api.controller.core.validation.SegurancaSenha.RequisitosSenha.*;

@Getter
@Setter
public class UsuarioInput {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @SegurancaSenha(requisitos = {MINUSCULO, MAIUSCULO, DIGITO, CARACTER_ESPECIAL})
    @NotBlank
    private String senha;
    
}
