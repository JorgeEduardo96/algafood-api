package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.SegurancaSenha;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {

    @ApiModelProperty(example = "joao.ger@algafood.com.br", required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(example = "João Silva", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Jr12322!;", required = true)
    @SegurancaSenha
    @NotBlank
    private String senha;
    
}
