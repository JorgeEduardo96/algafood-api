package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CozinhaController {

	private final CozinhaModelAssembler assembler;

	private final CozinhaRepository repository;

	private final CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public Page<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
		Page<Cozinha> cozinhas = repository.findAll(pageable);
		List<CozinhaModel> cozinhasModel = assembler.toCollecionModel(cozinhas.getContent());

		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhas.getTotalElements());

		return cozinhasModelPage;
	}

	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		return this.cadastroCozinhaService.buscarOuFalhar(cozinhaId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return this.cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = this.cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return this.cadastroCozinhaService.salvar(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		this.cadastroCozinhaService.excluir(cozinhaId);
	}

	@GetMapping("/buscar-primeiro")
	public ResponseEntity<Optional<Cozinha>> buscarPrimeiro() {
		return ResponseEntity.ok(this.repository.buscarPrimeiro());
	}

}
