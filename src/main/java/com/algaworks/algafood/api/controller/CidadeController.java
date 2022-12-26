package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CidadeController implements CidadeControllerOpenApi {

	private final CidadeRepository repository;

	private final CadastroCidadeService cadastroCidadeService;

	private final CidadeModelAssembler assembler;

	private final CidadeInputDisassembler disassembler;

	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<CidadeModel> cidadeModels = this.assembler.toCollectionModel(this.repository.findAll());

		cidadeModels.forEach(cidadeModel -> {
			cidadeModel.add(linkTo(methodOn(CidadeController.class)
					.buscar(cidadeModel.getId())).withSelfRel());

			cidadeModel.add(linkTo(methodOn(CidadeController.class)
					.listar()).withRel("cidades"));

			cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
					.buscar(cidadeModel.getEstado().getId())).withSelfRel());
		});

		CollectionModel collectionModel = CollectionModel.of(cidadeModels);

		collectionModel.add(linkTo(CidadeController.class).withSelfRel());

		return collectionModel;
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		CidadeModel cidadeModel = this.assembler.toModel(this.cadastroCidadeService.buscarOuFalhar(cidadeId));

		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.buscar(cidadeModel.getId())).withSelfRel());

		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));

		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeModel.getEstado().getId())).withSelfRel());

		return cidadeModel;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {

			Cidade cidade = this.cadastroCidadeService.salvar(disassembler.toDomainObject(cidadeInput));
			CidadeModel cidadeModel = assembler.toModel(cidade);

			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

			return cidadeModel;
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = this.cadastroCidadeService.buscarOuFalhar(cidadeId);
		this.disassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		try {
			return this.assembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
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
