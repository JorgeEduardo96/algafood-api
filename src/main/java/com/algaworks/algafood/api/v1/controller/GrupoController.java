package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoRepository repository;

    private final CadastroGrupoService cadastroGrupoService;

    private final GrupoModelAssembler grupoModelAssembler;

    private final GrupoInputDisassembler grupoInputDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> todosGrupos = repository.findAll();

        return grupoModelAssembler.toCollectionModel(todosGrupos);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return this.grupoModelAssembler.toModel(this.cadastroGrupoService.buscarOuFalhar(grupoId));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        return this.grupoModelAssembler.toModel(this.cadastroGrupoService.salvar(
                this.grupoInputDisassembler.toDomainObject(grupoInput)
        ));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = this.cadastroGrupoService.buscarOuFalhar(grupoId);
        this.grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        try {
            return this.grupoModelAssembler.toModel(this.cadastroGrupoService.salvar(grupoAtual));
        } catch (GrupoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        this.cadastroGrupoService.excluir(grupoId);
    }

}
