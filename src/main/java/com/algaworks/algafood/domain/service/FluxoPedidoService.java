package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final BuscaPedidoService buscaPedidoService;
    private final PedidoRepository repository;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = buscaPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();

        repository.save(pedido);
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

        repository.save(pedido);
    }

}
