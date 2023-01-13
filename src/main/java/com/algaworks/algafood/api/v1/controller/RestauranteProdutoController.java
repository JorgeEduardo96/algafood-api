package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	private final CadastroProdutoService cadastroProdutoService;

	private final ProdutoModelAssembler assembler;

	private final ProdutoInputDisassembler disassembler;

	private final CadastroRestauranteService cadastroRestauranteService;

	private final ProdutoRepository repository;

	private final AlgaLinks algaLinks;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
												@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		List<Produto> todosProdutos = null;

		if (incluirInativos) {
			todosProdutos = repository.findTodosByRestaurante(restaurante);
		} else {
			todosProdutos = repository.findAtivosByRestaurante(restaurante);
		}

		return assembler.toCollectionModel(todosProdutos)
				.add(algaLinks.linkToProdutos(restauranteId));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		return assembler.toModel(produto);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		Produto produto = disassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProdutoService.salvar(produto);

		return this.assembler.toModel(produto);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
								  @RequestBody ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		disassembler.copyToDomainObject(produtoInput, produtoAtual);

		produtoAtual = cadastroProdutoService.salvar(produtoAtual);

		return assembler.toModel(produtoAtual);
	}

}
