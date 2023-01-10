package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirma o recebimento do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado")
    })
    ResponseEntity<Void> confirmar(@ApiParam(value="ID de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true) String codigo);

    @ApiOperation("Entrega do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido entregue"),
            @ApiResponse(code = 404, message = "Pedido não encontrado")
    })
    ResponseEntity<Void> entregar(@ApiParam(value="ID de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true) String codigo);

    @ApiOperation("Cancelamento do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado")
    })
    ResponseEntity<Void> cancelar(@ApiParam(value="ID de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true) String codigo);

}
