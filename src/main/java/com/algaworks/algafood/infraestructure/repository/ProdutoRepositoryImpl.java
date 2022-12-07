package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.query.ProdutoRepositoryQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return entityManager.merge(foto);
    }

    @Transactional
    @Override
    public FotoProduto saveAndFlush(FotoProduto foto) {
        foto = entityManager.merge(foto);
        entityManager.flush();
        return foto;
    }

    @Transactional
    @Override
    public void delete(FotoProduto foto) {
        entityManager.remove(foto);
    }

    @Transactional
    @Override
    public void deleteAndFlush(FotoProduto fotoProduto) {
        entityManager.remove(fotoProduto);
        entityManager.flush();
    }
}
