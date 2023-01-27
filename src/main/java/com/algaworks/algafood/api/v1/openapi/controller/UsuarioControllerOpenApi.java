package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuários", description = "Gerenciamento dos usuários")
public interface UsuarioControllerOpenApi {

    CollectionModel<UsuarioModel> listar();

    UsuarioModel buscar(Long usuarioId);

    UsuarioModel adicionar(UsuarioInput usuarioInput);

    UsuarioModel atualizar(Long usuarioId, UsuarioSemSenhaInput usuarioSemSenhaInput);

    void alterarSenha(Long usuarioId, SenhaInput senhaInput);

    void deletar(Long usuarioId);

}
