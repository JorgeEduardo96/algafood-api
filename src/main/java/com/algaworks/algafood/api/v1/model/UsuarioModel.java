package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "usuarios")
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "vendas5@algafood.com.br")
    private String email;
    @ApiModelProperty(example = "Vendas 5")
    private String nome;

}
