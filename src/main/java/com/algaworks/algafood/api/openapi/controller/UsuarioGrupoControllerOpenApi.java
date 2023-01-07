package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    CollectionModel<GrupoModel> listar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
                    Long usuarioId);

    @ApiOperation("Associação de usuário com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                  @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Desassociação de usuário com grupo")
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                     @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);


}