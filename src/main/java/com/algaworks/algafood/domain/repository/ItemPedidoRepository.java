package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.ItemPedido;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends CustomJpaRepository<ItemPedido, Long> {
}
