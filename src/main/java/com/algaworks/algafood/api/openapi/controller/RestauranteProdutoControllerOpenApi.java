package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos associados ao restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrada", response = Problem.class)
    })
    List<ProdutoModel> listar(@ApiParam(value="ID do restaurante", example = "1", required = true)
                                      Long restauranteId,@ApiParam(value="Flag para incluir inativos na listagem",
            example = "1") boolean incluirInativos);

    @ApiOperation("Busca os produtos associados ao restaurante pelo código do produto")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrada", response = Problem.class)
    })
    ProdutoModel buscar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                        @ApiParam(value="ID do produto", example = "1", required = true) Long produtoId);

    @ApiOperation("Salva um produto no restaurante com o código informado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrada", response = Problem.class)
    })
    ProdutoModel salvar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                        @ApiParam(name="corpo", value="Representação de um novo produto", required = true)
                                ProdutoInput produtoInput);

    @ApiOperation("Atualiza um produto no restaurante com o código informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrada", response = Problem.class)
    })
    ProdutoModel atualizar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                           @ApiParam(value="ID do produto", example = "1", required = true) Long produtoId,
                           @ApiParam(name="corpo", value="Representação de um novo produto", required = true)
                                   ProdutoInput produtoInput);
}
