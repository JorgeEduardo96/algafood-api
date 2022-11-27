package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscaPedidoService {

    private final PedidoRepository repository;

    public Pedido buscarOuFalhar(String codigo) {
        return this.repository.findByCodigo(codigo).orElseThrow(()
                -> new PedidoNaoEncontradoException(codigo));
    }

}
