package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.assembler.UsuarioSemSenhaInputDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
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
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioRepository usuarioRepository;

    private final CadastroUsuarioService cadastroUsuarioService;

    private final UsuarioModelAssembler assembler;

    private final UsuarioInputDisassembler disassembler;

    private final UsuarioSemSenhaInputDisassembler usuarioSemSenhaInputDisassembler;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        return this.assembler.toCollectionModel(usuarioRepository.findAll());
    }

    @Override
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = this.cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return this.assembler.toModel(usuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        return this.assembler.toModel(cadastroUsuarioService.salvar(
                this.disassembler.toDomainObject(usuarioInput)
        ));
    }

    @Override
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
        Usuario usuarioAtual = this.cadastroUsuarioService.buscarOuFalhar(usuarioId);
        this.usuarioSemSenhaInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);
        return this.assembler.toModel(this.cadastroUsuarioService.salvar(usuarioAtual));
    }

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
