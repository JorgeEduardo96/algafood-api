package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    @Schema(example = "Bobó de Camarão")
    private String nome;

    @NotBlank
    @Schema(example = "400g de Bobó de Camarão, acompanhado de arroz e batata frita")
    private String descricao;

    @PositiveOrZero
    @Schema(example = "7,99")
    private BigDecimal preco;

    @NotNull
    @Schema(example = "true")
    private boolean ativo;

}
