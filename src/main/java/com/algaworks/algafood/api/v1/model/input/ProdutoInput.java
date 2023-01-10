package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @ApiModelProperty(example = "Carne de Sol", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "500g de carne de sol, acompanhada de arroz e feijão", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "47.50", required = true)
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private boolean ativo;

}
