package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "permissoes")
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "EDITAR_USUARIOS_GRUPOS_PERMISSOES")
    private String nome;
    @Schema(example = "Permite criar ou editar usuários")
    private String descricao;

}
