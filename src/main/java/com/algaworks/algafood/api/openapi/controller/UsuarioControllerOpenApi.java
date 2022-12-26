package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioSemSenhaInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    List<UsuarioModel> listar();

    @ApiOperation("Busca um usuário")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel buscar(@ApiParam(value="ID de um usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado")
    })
    UsuarioModel adicionar(@ApiParam(name="corpo", value="Representação de um novo usuário",
            required = true) UsuarioInput usuarioInput);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel atualizar(@ApiParam(value="ID de um usuário", example = "1", required = true) Long usuarioId,
                           @ApiParam(name="corpo", value="Representação de um usuário com os novos dados",
                                   required = true) UsuarioSemSenhaInput usuarioSemSenhaInput);
    @ApiOperation("Atualiza a senha de um usuário pelo seu ID")
    void alterarSenha(@ApiParam(value="ID de um usuário", example = "1", required = true) Long usuarioId,
                      @ApiParam(name="corpo", value="Payload com senha atual e nova para alteração",
                              required = true) SenhaInput senhaInput);

    @ApiOperation("Exclui um usuário por ID")
    void deletar(@ApiParam(value="ID de um usuário", example = "1", required = true) Long usuarioId);

}
