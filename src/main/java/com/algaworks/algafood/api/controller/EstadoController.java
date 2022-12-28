package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EstadoController implements EstadoControllerOpenApi {

	private final EstadoRepository repository;

	private final CadastroEstadoService cadastroEstadoService;

	private final EstadoModelAssembler assembler;

	private final EstadoInputDisassembler disassembler;

	@Override
	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		return assembler.toCollectionModel(this.repository.findAll());
	}

	@Override
	@GetMapping("/{idEstado}")
	public EstadoModel buscar(@PathVariable Long idEstado) {
		return assembler.toModel(this.cadastroEstadoService.buscarOuFalhar(idEstado));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		return assembler.toModel(this.cadastroEstadoService.salvar(disassembler.toDomainObject(estadoInput)));
	}

	@Override
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = this.cadastroEstadoService.buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estadoInput, estadoAtual, "id");
		return assembler.toModel(this.cadastroEstadoService.salvar(estadoAtual));
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		this.cadastroEstadoService.excluir(estadoId);
	}

}
