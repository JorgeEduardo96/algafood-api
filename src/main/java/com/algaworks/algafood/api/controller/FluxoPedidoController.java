package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pedidos/{codigo}")
@RequiredArgsConstructor
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    private final FluxoPedidoService fluxoPedidoService;

    @Override
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String codigo) {
        this.fluxoPedidoService.confirmar(codigo);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigo) {
        this.fluxoPedidoService.entregar(codigo);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String codigo) {
        this.fluxoPedidoService.cancelar(codigo);

        return ResponseEntity.noContent().build();
    }
}
