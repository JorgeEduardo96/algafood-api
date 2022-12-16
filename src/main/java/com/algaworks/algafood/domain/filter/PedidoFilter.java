package com.algaworks.algafood.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoFilter {

    @ApiModelProperty(example = "1", value = "ID do cliente associado ao pedido")
    private Long clienteId;
    @ApiModelProperty(example = "1", value = "ID do restaurante associado ao pedido")
    private Long restauranteId;
    @ApiModelProperty(example = "2019-11-01T00:00:00Z", value = "Data inicial da criação do pedido")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;
    @ApiModelProperty(example = "2019-11-01T00:00:00Z", value = "Data final da criação do pedido")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;


}
