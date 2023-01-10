package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.SegurancaSenha;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @ApiModelProperty(example = "Jr12322!;", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "Jr12322!;", required = true)
    @NotBlank
    @SegurancaSenha
    private String senhaNova;

}
