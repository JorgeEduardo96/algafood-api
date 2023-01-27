package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    CollectionModel<PermissaoModel> listar(Long grupoId);

    ResponseEntity<Void> desassociar(Long grupoId, Long permissaoId);

    ResponseEntity<Void> associar(Long grupoId, Long permissaoId);
}

