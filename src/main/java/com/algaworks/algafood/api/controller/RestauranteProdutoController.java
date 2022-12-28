package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	private final CadastroProdutoService cadastroProdutoService;

	private final ProdutoModelAssembler assembler;

	private final ProdutoInputDisassembler disassembler;

	private final CadastroRestauranteService cadastroRestauranteService;

	private final ProdutoRepository repository;

	@Override
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId,
									 @RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		List<Produto> todosProdutos = incluirInativos ? repository.findTodosByRestaurante(restaurante) :
				repository.findAtivosByRestaurante(restaurante);

		return assembler.toCollectionModel(todosProdutos);
	}

	@Override
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		return assembler.toModel(produto);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel salvar(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		Produto produto = disassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProdutoService.salvar(produto);

		return this.assembler.toModel(produto);
	}

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
