package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  EnderecoInput {

    @NotBlank
    @Schema(example = "52041375")
    private String cep;

    @NotBlank
    @Schema(example = "Rua Gerson de Barros Pinto")
    private String logradouro;

    @NotBlank
    @Schema(example = "405")
    private String numero;

    @Schema(example = "Ap. 301")
    private String complemento;

    @NotBlank
    @Schema(example = "Arruda")
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}
