package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @NotNull
    @Valid
    private RestauranteIdInput restaurante;

    @NotNull
    @Valid
    private EnderecoInput enderecoEntrega;

    @NotNull
    @Valid
    private FormaPagamentoIdInput formaPagamento;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<ItemPedidoInput> itens;


}
