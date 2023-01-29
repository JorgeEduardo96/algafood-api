package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @Schema(example = "5204139")
    private String cep;
    @Schema(example = "Rua João Primo")
    private String logradouro;
    @Schema(example = "205")
    private String numero;
    @Schema(example = "Casa")
    private String complemento;
    @Schema(example = "Ponto de Partida")
    private String bairro;
    private CidadeResumoModel cidade;

}
