package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Formas de pagamento", description = "Gerenciamento das formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    ResponseEntity<FormaPagamentoModel> buscar(Long idFormaPagamento, ServletWebRequest request);

    FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput);

    FormaPagamentoModel atualizar(Long idFormaPagamento, FormaPagamentoInput formaPagamentoInput);

    void remover(Long idFormaPagamento);

}
