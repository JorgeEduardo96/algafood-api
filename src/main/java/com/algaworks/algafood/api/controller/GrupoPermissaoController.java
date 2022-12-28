package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{idGrupo}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController {

    private final CadastroGrupoService cadastroGrupoService;

    private final PermissaoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<List<PermissaoModel>> listar(@PathVariable Long idGrupo) {
        Grupo grupo = this.cadastroGrupoService.buscarOuFalhar(idGrupo);
        return ResponseEntity.ok(this.assembler.toCollectionModel(grupo.getPermissoes()));
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void incluirPermissao(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.incluirPermissao(idGrupo, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.excluirPermissao(idGrupo, permissaoId);
    }

}
