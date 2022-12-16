package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumoModel {

    @ApiModelProperty(example = "1")
    private String codigo;
    @ApiModelProperty(example = "75,99")
    private BigDecimal subTotal;
    @ApiModelProperty(example = "10,90")
    private BigDecimal taxaFrete;
    @ApiModelProperty(example = "86,89")
    private BigDecimal valorTotal;
    @ApiModelProperty(example = "CRIADO")
    private StatusPedido status;
    @ApiModelProperty(example = "2019-11-01T00:00:00Z")
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;

}
