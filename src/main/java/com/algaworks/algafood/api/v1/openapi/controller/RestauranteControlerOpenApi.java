package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerenciamento dos restaurantes")
public interface RestauranteControlerOpenApi {

    CollectionModel<RestauranteBasicoModel> listar();

    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    RestauranteModel buscar(Long restauranteId);

    RestauranteModel adicionar(RestauranteInput restauranteInput);

    RestauranteModel atualizar(Long restauranteId, RestauranteInput restauranteInput);

    void ativacoes(List<Long> restauranteIds);

    void inativacoes(List<Long> restauranteIds);

    ResponseEntity<Void> ativar(Long restauranteId);

    ResponseEntity<Void> inativar(Long restauranteId);

    ResponseEntity<Void> abrir(Long restauranteId);

    ResponseEntity<Void> fechar(Long restauranteId);

}
