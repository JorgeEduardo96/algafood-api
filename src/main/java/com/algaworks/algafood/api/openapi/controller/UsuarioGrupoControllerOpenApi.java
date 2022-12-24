package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associadas a um usuário")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<List<GrupoModel>> listar(@ApiParam(value = "ID do usuário", example = "1", required = true)
                                            Long usuarioId);

    @ApiOperation("Associação de usuário com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void adicionarGrupo(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                        @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Desassociação de usuário com grupo")
    void desassociarGrupo(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                          @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);


}
