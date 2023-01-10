package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<FormaPagamentoModel> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
                    Long restauranteId);

    @ApiOperation(value = "Desassocia uma forma de pagamento ao restaurante de código informado")
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante", example = "1",
            required = true) Long restauranteId, @ApiParam(value = "ID da forma de pagamento", example = "1",
            required = true) Long formaPagamentoId);

    @ApiOperation("Associa uma forma de pagamento ao restaurante de código informado")
    ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante", example = "1",
            required = true) Long restauranteId, @ApiParam(value = "ID da forma de pagamento", example = "1",
            required = true) Long formaPagamentoId);

}
