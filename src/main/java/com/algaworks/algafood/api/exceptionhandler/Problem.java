package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400")
    private Integer status;
    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
    private String type;
    @ApiModelProperty(example = "Dados Inválidos")
    private String title;
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;
    @ApiModelProperty(example = "2022-12-01T18:09:02.70844Z")
    private OffsetDateTime timestamp;
    @ApiModelProperty("Lista de objetos ou campos que geraram o erro (opcional)")
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {
        @ApiModelProperty(example = "preco")
        private String name;
        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;
    }
}
