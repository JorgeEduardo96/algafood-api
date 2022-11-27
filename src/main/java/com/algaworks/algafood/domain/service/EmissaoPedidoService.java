package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final CadastroCidadeService cadastroCidadeService;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final CadastroProdutoService cadastroProdutoService;

    @Transactional
    public Pedido emissaoPedido(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.definirFrete();
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setRestaurante(restaurante);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento))
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
    }

    private void validarItens(Pedido pedido) {
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
