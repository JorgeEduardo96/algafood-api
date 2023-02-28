package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroProdutoService {

    private final ProdutoRepository repository;

    @Transactional
    public Produto salvar(Produto produto) {
        return this.repository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }


}
