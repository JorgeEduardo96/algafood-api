package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ValidadorPedido validadorPedido;
    private final ValidadorItensPedido validadorItensPedido;

    @Transactional
    public Pedido emissaoPedido(Pedido pedido) {
        validadorPedido.execute(pedido);
        validadorItensPedido.execute(pedido);

        calcularValoresPedido(pedido);

        return pedidoRepository.save(pedido);
    }

    private void calcularValoresPedido(Pedido pedido) {
        pedido.definirFrete();
        pedido.calcularValorTotal();
    }

}
