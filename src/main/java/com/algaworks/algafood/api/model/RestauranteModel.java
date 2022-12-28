package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Set;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Restaurante da Maria")
    private String nome;
    private CozinhaModel cozinha;
    @ApiModelProperty(example = "7.50")
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;
    private Set<FormaPagamentoModel> formasPagamento;

}
