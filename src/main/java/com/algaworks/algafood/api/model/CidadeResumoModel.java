package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

    private Long id;
    private String nome;
    private String estado;

}
