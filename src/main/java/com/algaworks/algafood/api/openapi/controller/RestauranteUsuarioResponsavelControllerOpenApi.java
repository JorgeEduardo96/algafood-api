package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

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
    void ativar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                @ApiParam(value="ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Inativa um restaurante")
    void inativar(@ApiParam(value="ID do restaurante", example = "1", required = true) Long restauranteId,
                  @ApiParam(value="ID do usuário", example = "1", required = true) Long usuarioId);

}
