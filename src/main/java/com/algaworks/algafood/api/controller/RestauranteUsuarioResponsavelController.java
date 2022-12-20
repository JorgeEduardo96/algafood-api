package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	private final CadastroRestauranteService cadastroRestauranteService;

	private final UsuarioModelAssembler usuarioModelAssembler;

	@GetMapping
	public ResponseEntity<List<UsuarioModel>> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		return ResponseEntity.ok(this.usuarioModelAssembler.toCollectionModel(restaurante.getUsuarioResponsavel()));
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}

}
