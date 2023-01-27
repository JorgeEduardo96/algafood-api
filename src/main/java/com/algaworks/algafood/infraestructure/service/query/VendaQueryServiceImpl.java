package com.algaworks.algafood.infraestructure.service.query;

import com.algaworks.algafood.api.v1.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionConvertTzDate = builder.function("convert_tz", Date.class,
               root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));

        var funtionDateDataCriacao =
                builder.function("date", Date.class, functionConvertTzDate);

        var selection = builder.construct(VendaDiaria.class,
                funtionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.where(construirPredicates(filtro, builder, root));
        query.groupBy(funtionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] construirPredicates(VendaDiariaFilter filtro, CriteriaBuilder builder, Root<Pedido> root) {
        var predicates = new ArrayList<Predicate>();

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
        }

        if(filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if(filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
