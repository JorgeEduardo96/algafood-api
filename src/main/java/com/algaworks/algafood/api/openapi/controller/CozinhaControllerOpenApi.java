package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    Page<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    CozinhaModel buscar(@ApiParam(value="ID de uma cozinha", example = "1") Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada")
    })
    CozinhaModel adicionar(@ApiParam(name="corpo", value="Representação de uma nova cozinha") CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma cozinha pór ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModel atualizar(@ApiParam(value="ID de uma cozinha", example = "1") Long cozinhaId,
                      @ApiParam(name="corpo",
                              value="Representação de uma cidade com os novos dados") CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    void remover(@ApiParam(value="ID de uma cozinha", example = "1") Long cozinhaId);

}
