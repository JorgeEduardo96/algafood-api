package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    ResponseEntity<Void> confirmar(String codigo);

    ResponseEntity<Void> entregar(String codigo);

    ResponseEntity<Void> cancelar(String codigo);

}
