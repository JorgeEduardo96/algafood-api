package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoRepository repository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(this.repository.listar());
	}

	@GetMapping("/{idEstado}")
	public ResponseEntity<Estado> buscar(@PathVariable Long idEstado) {
		Estado estado = this.repository.buscar(idEstado);
		if (estado != null) {
			return ResponseEntity.ok(estado);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Estado estado) {
		try {
			estado = this.cadastroEstadoService.salvar(estado);
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> editar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		Estado estadoAtual = this.repository.buscar(estadoId);
		if (estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			this.cadastroEstadoService.salvar(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId) {
		try {
			this.cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

}
