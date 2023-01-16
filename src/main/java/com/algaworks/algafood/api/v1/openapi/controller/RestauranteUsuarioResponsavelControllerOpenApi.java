package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os responsáveis ao restaurante do código associado")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado")
    })
    CollectionModel<UsuarioModel> listar(@ApiParam(value="ID do restaurante",
            example = "1", required = true) Long restauranteId);

    @ApiOperation("Ativa um restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado")
    })
    ResponseEntity<Void> associar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                  @ApiParam(value="ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Inativa um restaurante")
    ResponseEntity<Void> desassociar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value="ID do usuário", example = "1", required = true) Long usuarioId);

}