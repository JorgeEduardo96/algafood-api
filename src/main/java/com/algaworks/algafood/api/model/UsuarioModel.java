package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "vendas5@algafood.com.br")
    private String email;
    @ApiModelProperty(example = "Vendas 5")
    private String nome;

}
