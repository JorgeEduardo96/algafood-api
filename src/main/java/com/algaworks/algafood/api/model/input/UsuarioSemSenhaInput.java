package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioSemSenhaInput {

    @ApiModelProperty(example = "vendas5@algafood.com.br")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(example = "Vendas 5")
    @NotBlank
    private String nome;

}
