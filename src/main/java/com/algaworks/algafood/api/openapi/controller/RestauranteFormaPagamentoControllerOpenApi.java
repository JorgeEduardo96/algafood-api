package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation(value = "Lista as formas de pagamento associadas ao restaurante de código informado")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado")
    })
    ResponseEntity<List<FormaPagamentoModel>> listar(@ApiParam(value = "ID do restaurante", example = "1",
            required = true) Long restauranteId);

    @ApiOperation(value = "Desassocia uma forma de pagamento ao restaurante de código informado")
    void desassociar(@ApiParam(value = "ID do restaurante", example = "1",
            required = true) Long restauranteId, @ApiParam(value = "ID da forma de pagamento", example = "1",
            required = true) Long formaPagamentoId);

    @ApiOperation("Associa uma forma de pagamento ao restaurante de código informado")
    void associar(@ApiParam(value = "ID do restaurante", example = "1",
            required = true) Long restauranteId, @ApiParam(value = "ID da forma de pagamento", example = "1",
            required = true) Long formaPagamentoId);

}
