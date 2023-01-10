package com.algaworks.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("CidadeInput")
public class CidadeInputV2 {

    @ApiModelProperty(example = "Recife", required = true)
    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;

}
