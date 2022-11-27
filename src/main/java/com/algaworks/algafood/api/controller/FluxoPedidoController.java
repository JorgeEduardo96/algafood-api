package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pedidos/{codigo}")
@RequiredArgsConstructor
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigo) {
        this.fluxoPedidoService.confirmar(codigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigo) {
        this.fluxoPedidoService.entregar(codigo);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigo) {
        this.fluxoPedidoService.cancelar(codigo);
    }
}
