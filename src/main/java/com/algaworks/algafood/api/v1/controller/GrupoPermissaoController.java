package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/grupos/{idGrupo}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final CadastroGrupoService cadastroGrupoService;

    private final PermissaoModelAssembler assembler;

    private final AlgaLinks algaLinks;

    private final AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long idGrupo) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(idGrupo);

        CollectionModel<PermissaoModel> permissoesModel
                = assembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        permissoesModel.add(algaLinks.linkToGrupoPermissoes(idGrupo));

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesModel.add(algaLinks.linkToGrupoPermissaoAssociacao(idGrupo, "associar"));

            permissoesModel.getContent().forEach(permissaoModel -> {
                permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
                        idGrupo, permissaoModel.getId(), "desassociar"));
            });
        }

        return permissoesModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.incluirPermissao(idGrupo, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long idGrupo, @PathVariable Long permissaoId) {
        this.cadastroGrupoService.excluirPermissao(idGrupo, permissaoId);

        return ResponseEntity.noContent().build();
    }

}
