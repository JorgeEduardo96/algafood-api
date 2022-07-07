package com.algaworks.algafood.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(this.repository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
		return this.cadastroCidadeService.buscarOuFalhar(cidadeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return this.cadastroCidadeService.salvar(cidade);
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = this.cadastroCidadeService.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return this.cadastroCidadeService.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		this.cadastroCidadeService.excluir(cidadeId);
	}

}