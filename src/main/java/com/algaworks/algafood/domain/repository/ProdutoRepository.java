package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.query.ProdutoRepositoryQueries;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("from Produto p where restaurante.id = :restauranteId and id = :produtoId")
    Optional<Produto> findById(Long restauranteId, Long produtoId);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p where p.restaurante.id = :restauranteId" +
            " and f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
