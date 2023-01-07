package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupos/{idGrupo}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final CadastroGrupoService cadastroGrupoService;

    private final PermissaoModelAssembler assembler;

    private final AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoModel> permissoesModel
                = assembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(algaLinks.linkToGrupoPermissoes(grupoId))
                .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel ->
                permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(),
                        "desassociar")));

        return permissoesModel;
    }

    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.incluirPermissao(idGrupo, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.excluirPermissao(idGrupo, permissaoId);

        return ResponseEntity.noContent().build();
    }

}
