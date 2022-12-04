package com.algaworks.algafood.domain.repository.query;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    FotoProduto saveAndFlush(FotoProduto foto);

    void delete(FotoProduto foto);

}
