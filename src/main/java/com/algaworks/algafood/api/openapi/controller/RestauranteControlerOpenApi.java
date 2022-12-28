package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControlerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @ApiOperation("Busca um restaurante por ID")
    RestauranteModel buscar(@ApiParam(value="ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado")
    })
    RestauranteModel adicionar(@ApiParam(name="corpo", value="Representação de um novo restaurante")
                               RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel atualizar(@ApiParam(value="ID de um restaurante", example = "1")
                               Long restauranteId, @ApiParam(name="corpo", value="Representação de um novo restaurante")
                               RestauranteInput restauranteInput);

    @ApiOperation("Ativa um grupo de restaurantes por seus respectivos IDs")
    void ativacoes(List<Long> restauranteIds);

    @ApiOperation("Inativa um grupo de restaurantes por seus respectivos IDs")
    void inativacoes(List<Long> restauranteIds);

    @ApiOperation("Ativa um restaurante por ID")
    ResponseEntity<Void> ativar(@ApiParam(value="ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    ResponseEntity<Void> inativar(@ApiParam(value="ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Abre um restaurante por ID")
    ResponseEntity<Void> abrir(@ApiParam(value="ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechar(@ApiParam(value="ID de um restaurante", example = "1") Long restauranteId);

}
