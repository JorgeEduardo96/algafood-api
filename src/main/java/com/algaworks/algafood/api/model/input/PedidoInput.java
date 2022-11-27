package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
