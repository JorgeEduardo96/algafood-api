package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorItensPedido {

    private final CadastroProdutoService cadastroProdutoService;

    public void execute(Pedido pedido) {
        pedido.getItens().forEach(itemPedido -> {
            Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(),
                    itemPedido.getProduto().getId());

            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.calcularPrecoTotal();
        });
    }

}
