package com.algaworks.algafood.domain.model.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissao) {
        this(String.format("Não existe um cadastro de permissão com código %d", permissao));
    }
}
