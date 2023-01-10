package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.algaworks.algafood.api.ResourceUriHelper.addUriInResponseHeader;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CidadeControllerV2 {

	private final CidadeRepository repository;

	private final CadastroCidadeService cadastroCidadeService;

	private final CidadeModelAssemblerV2 assembler;

	private final CidadeInputDisassemblerV2 disassembler;

	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		return this.assembler.toCollectionModel(this.repository.findAll());
	}

	@GetMapping(value = "/{cidadeId}")
	public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
		return this.assembler.toModel(this.cadastroCidadeService.buscarOuFalhar(cidadeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidade = this.cadastroCidadeService.salvar(disassembler.toDomainObject(cidadeInput));
			CidadeModelV2 cidadeModelv2 = assembler.toModel(cidade);

			addUriInResponseHeader(cidadeModelv2.getIdCidade());

			return cidadeModelv2;
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@PutMapping(value = "/{cidadeId}")
	public CidadeModelV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {
		Cidade cidadeAtual = this.cadastroCidadeService.buscarOuFalhar(cidadeId);
		this.disassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		try {
			return this.assembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

//	@DeleteMapping("/{cidadeId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long cidadeId) {
//		this.cadastroCidadeService.excluir(cidadeId);
//	}

}
