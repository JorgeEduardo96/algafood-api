package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroPermissaoService {

    private static final String MSG_PERMISSAO_EM_USO =
            "Permissão de código %d não pode ser removida, pois está em uso";


    private final PermissaoRepository repository;

    public Permissao buscarOuFalhar(Long idPermissao) {
        return this.repository.findById(idPermissao).orElseThrow(()
                -> new PermissaoNaoEncontradaException(idPermissao));
    }

}
