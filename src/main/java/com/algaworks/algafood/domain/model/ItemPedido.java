package com.algaworks.algafood.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(nullable = false, name = "preco_total")
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();

        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
    }
}
