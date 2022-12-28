package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista as estados")
    CollectionModel<EstadoModel> listar();

    @ApiOperation("Busca um estado por ID")
    EstadoModel buscar(@ApiParam(value="ID de um estado", example = "1", required = true) Long idEstado);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado")
    })
    EstadoModel adicionar(@ApiParam(name="corpo", value="Representação de um novo estado", required = true)
                          EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrada", response = Problem.class)
    })
    EstadoModel atualizar(@ApiParam(value="ID de um estado", example = "1", required = true) Long estadoId,
                          @ApiParam(name="corpo", value="Representação de um novo estado", required = true)
                          EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    void remover(Long estadoId);

}
