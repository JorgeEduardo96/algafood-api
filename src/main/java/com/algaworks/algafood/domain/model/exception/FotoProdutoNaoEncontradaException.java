package com.algaworks.algafood.domain.model.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradaException(String mensagem) { super(mensagem); }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de foto produto de código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }
}
