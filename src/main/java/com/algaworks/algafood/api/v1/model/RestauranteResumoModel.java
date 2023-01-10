package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Restaurante da Maria")
    private String nome;

}
