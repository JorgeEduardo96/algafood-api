package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CozinhaController implements CozinhaControllerOpenApi {

	private final CozinhaModelAssembler assembler;

	private final CozinhaInputDisassembler disassembler;

	private final CozinhaRepository repository;

	private final CadastroCozinhaService cadastroCozinhaService;

	private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@Override
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
		return pagedResourcesAssembler.toModel(repository.findAll(pageable), assembler);
	}

	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return assembler.toModel(this.cadastroCozinhaService.buscarOuFalhar(cozinhaId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return assembler.toModel(this.cadastroCozinhaService.salvar(disassembler.toDomainObject(cozinhaInput)));
	}

	@Override
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaInput cozinha) {
		Cozinha cozinhaAtual = this.cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return assembler.toModel(this.cadastroCozinhaService.salvar(cozinhaAtual));
	}

	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		this.cadastroCozinhaService.excluir(cozinhaId);
	}

}
