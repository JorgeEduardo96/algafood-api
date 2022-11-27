package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final BuscaPedidoService buscaPedidoService;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = buscaPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = buscaPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = buscaPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
    }

}
