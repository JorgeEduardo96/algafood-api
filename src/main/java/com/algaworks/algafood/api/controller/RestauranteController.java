package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteController {

	private final RestauranteRepository repository;

	private final CadastroRestauranteService cadastroRestauranteService;

	private final RestauranteModelAssembler assembler;

	private final RestauranteInputDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<RestauranteModel>> listar() {
		return ResponseEntity.ok(this.assembler.toCollectionModel(this.repository.findAll()));
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return this.assembler.toModel(restaurante);
	}

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

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}


}
