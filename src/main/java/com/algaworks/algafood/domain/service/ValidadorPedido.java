package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorPedido {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final CadastroCidadeService cadastroCidadeService;
    private final CadastroUsuarioService cadastroUsuarioService;

    public void execute(Pedido pedido) {
        validarCliente(pedido);
        validarEnderecoEntrega(pedido);
        validarRestaurante(pedido);
        validarFormaPagamento(pedido);
    }

    private void validarRestaurante(Pedido pedido) {
        pedido.setRestaurante(cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId()));
    }

    private void validarFormaPagamento(Pedido pedido) {
        pedido.setFormaPagamento(cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId()));
        if (pedido.getRestaurante().naoAceitaFormaPagamento(pedido.getFormaPagamento()))
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    pedido.getFormaPagamento().getDescricao()));
    }

    private void validarCliente(Pedido pedido) {
        pedido.setCliente(cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId()));
    }

    private void validarEnderecoEntrega(Pedido pedido) {
        pedido.getEnderecoEntrega().setCidade(cadastroCidadeService.buscarOuFalhar(
                pedido.getEnderecoEntrega().getCidade().getId()));
    }

}
