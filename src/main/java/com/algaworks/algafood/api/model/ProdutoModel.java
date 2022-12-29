package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "produtos")
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Carne de Sol")
    private String nome;
    @ApiModelProperty(example = "500g de carne de sol, acompanhada de arroz e feijão")
    private String descricao;
    @ApiModelProperty(example = "47.50")
    private BigDecimal preco;
    @ApiModelProperty(example = "true")
    private boolean ativo;

}
