package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.SegurancaSenha;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {

    @NotBlank
    @Schema(example = "Primo190215;")
    private String senhaAtual;

    @NotBlank
    @SegurancaSenha
    @Schema(example = "Primo190227!/")
    private String senhaNova;

}
