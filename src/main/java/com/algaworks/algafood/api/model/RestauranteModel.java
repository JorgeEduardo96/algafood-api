package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    @ApiModelProperty(example = "1")
    @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
    private Long id;
    @ApiModelProperty(example = "Restaurante da Maria")
    @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
    private String nome;
    @JsonView( {RestauranteView.Resumo.class} )
    private CozinhaModel cozinha;
    @ApiModelProperty(example = "7.50")
    @JsonView( {RestauranteView.Resumo.class} )
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;

}
