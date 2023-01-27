package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Estado", description = "Gerenciamento dos estados")
public interface EstadoControllerOpenApi {

    CollectionModel<EstadoModel> listar();

    EstadoModel buscar(Long idEstado);

    EstadoModel adicionar(EstadoInput estadoInput);

    EstadoModel atualizar(Long estadoId, EstadoInput estadoInput);

    void remover(Long estadoId);

}
