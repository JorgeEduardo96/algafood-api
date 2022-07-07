package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(this.repository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
				"dataCadastro", "produtos");
		try {
			return this.cadastroRestauranteService.salvar(restauranteAtual);
		} catch (CozinhaNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
										@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
		merge(campos, restauranteAtual);
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		dadosOrigem.forEach((nomePropriedade, valorProprieade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}

}
