package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADo("Recurso não encontrado", "recurso-nao-encontrado"),
    ERRO_NEGOCIO("Violação de regra de negócio", "erro-negocio"),
    ENTIDADE_EM_USO("Entidade em uso", "entidade-em-uso"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "parametro-invalido"),
    ERRO_DE_SISTEMA("Erro de sistema", "erro-de-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "dados-invalidos"),
    ACESSO_NEGADO("Acesso Negado", "acess-negado");


    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://algafood.com.br/" + path;
    }
}
