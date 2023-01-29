package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Pix")
    private String descricao;

}
