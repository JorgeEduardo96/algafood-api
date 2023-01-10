package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Recife")
    private String nome;
    private EstadoModel estado;

}
