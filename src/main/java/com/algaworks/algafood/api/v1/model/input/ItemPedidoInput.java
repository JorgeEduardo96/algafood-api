package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    @Schema(example = "1")
    private Long produtoId;

    @NotNull
    @Positive
    @Schema(example = "5")
    private Long quantidade;

    @Schema(example = "Sem pimenta do reino")
    private String observacao;

}
