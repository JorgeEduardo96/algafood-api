package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroPermissaoService {

    private final PermissaoRepository repository;

    public Permissao buscarOuFalhar(Long idPermissao) {
        return this.repository.findById(idPermissao).orElseThrow(()
                -> new PermissaoNaoEncontradaException(idPermissao));
    }

}
