package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;
    @ApiModelProperty(example = "298.90")
    private BigDecimal subTotal;
    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;
    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;
    @ApiModelProperty(example = "CRIADO")
    private StatusPedido status;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;
    @ApiModelProperty(example = "2019-12-01T20:35:10Z")
    private OffsetDateTime dataEntrega;
    @ApiModelProperty(example = "2019-12-01T20:55:30Z")
    private OffsetDateTime dataCancelamento;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;
    private FormaPagamentoModel formaPagamento;
    private EnderecoModel enderecoEntrega;
    private List<ItemPedidoModel> itens;

}
