package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioSemSenhaInputDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioRepository usuarioRepository;

    private final CadastroUsuarioService cadastroUsuarioService;

    private final UsuarioModelAssembler assembler;

    private final UsuarioInputDisassembler disassembler;

    private final UsuarioSemSenhaInputDisassembler usuarioSemSenhaInputDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        return this.assembler.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = this.cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return this.assembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        return this.assembler.toModel(cadastroUsuarioService.salvar(
                this.disassembler.toDomainObject(usuarioInput)
        ));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @Override
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
        Usuario usuarioAtual = this.cadastroUsuarioService.buscarOuFalhar(usuarioId);
        this.usuarioSemSenhaInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);
        return this.assembler.toModel(this.cadastroUsuarioService.salvar(usuarioAtual));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @Override
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        this.cadastroUsuarioService.alterarSenha(usuarioId, senhaInput);
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        this.cadastroUsuarioService.excluir(usuarioId);
    }
}
