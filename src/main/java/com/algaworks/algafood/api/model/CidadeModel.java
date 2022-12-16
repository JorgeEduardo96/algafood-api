package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Setter
@Getter
public class CidadeModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Recife")
    private String nome;
    @ApiModelProperty(example = "1")
    private EstadoModel estado;

}
