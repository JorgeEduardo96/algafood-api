package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Restaurante da Maria")
    private String nome;
    @ApiModelProperty(example = "7.50")
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;

}
