package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(example = "52041-370", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Gerson de Barros Pinangé", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "205", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Casa")
    private String complemento;

    @ApiModelProperty(example = "Ponto de Parada", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}
