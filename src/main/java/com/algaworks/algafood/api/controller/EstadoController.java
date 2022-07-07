package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoRepository repository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(this.repository.findAll());
	}

	@GetMapping("/{idEstado}")
	public Estado buscar(@PathVariable Long idEstado) {
		return this.cadastroEstadoService.buscarOuFalhar(idEstado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return this.cadastroEstadoService.salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		Estado estadoAtual = this.cadastroEstadoService.buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return this.cadastroEstadoService.salvar(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		this.cadastroEstadoService.excluir(estadoId);
	}

}
