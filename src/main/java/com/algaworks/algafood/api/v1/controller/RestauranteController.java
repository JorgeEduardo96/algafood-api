package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControlerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteController implements RestauranteControlerOpenApi {

	private final RestauranteRepository repository;

	private final CadastroRestauranteService cadastroRestauranteService;

	private final RestauranteModelAssembler assembler;

	private final RestauranteInputDisassembler disassembler;

	private final RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	private final RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler
				.toCollectionModel(repository.findAll());
	}

	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler
				.toCollectionModel(repository.findAll());
	}

	@Override
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return this.assembler.toModel(restaurante);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			return this.assembler.toModel(cadastroRestauranteService.salvar(
					this.disassembler.toDomainObject(restauranteInput)));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@Override
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
									  @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
		this.disassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		try {
			return this.assembler.toModel(this.cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativacoes(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.ativacoes(restauranteIds);
		} catch (RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativacoes(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.inativacoes(restauranteIds);
		} catch (RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@Override
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}

}
