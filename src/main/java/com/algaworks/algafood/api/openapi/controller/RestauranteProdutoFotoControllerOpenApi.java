package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation(value = "Atualiza a foto de um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoModel atualizarFoto(@ApiParam(value="ID de um restaurante", example = "1", required = true)
                                   Long restauranteId,@ApiParam(value="ID de um produto", example = "1",
            required = true) Long produtoId, @ApiParam(name="corpo", value="Representação de um nova foto")
                                   FotoProdutoInput fotoProdutoInput,
                                   @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",
                                           required = true) MultipartFile arquivo)
            throws IOException;

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    FotoProdutoModel buscar(@ApiParam(value="ID de um restaurante", example = "1", required = true)
                            Long restauranteId,@ApiParam(value="ID de um produto", example = "1",
            required = true) Long produtoId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    void remover(@ApiParam(value="ID de um restaurante", example = "1", required = true)
                 Long restauranteId,@ApiParam(value="ID de um produto", example = "1",
            required = true) Long produtoId);

}
