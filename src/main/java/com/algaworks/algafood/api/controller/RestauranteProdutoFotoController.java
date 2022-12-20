package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto",
		produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestauranteProdutoFotoController {

	private final CatalogoFotoProdutoService catalogoFotoProdutoService;
	private final CadastroProdutoService cadastroProdutoService;
	private final FotoStorageService fotoStorageService;
	private final FotoProdutoModelAssembler assembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
										  @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
		fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
		fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
		fotoProduto.setProduto(cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId));

		return assembler.toModel(catalogoFotoProdutoService.salvar(fotoProduto,
				fotoProdutoInput.getArquivo().getInputStream()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return assembler.toModel(catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId));
	}

	@GetMapping(produces = { MediaType.ALL_VALUE })
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
										@PathVariable Long produtoId,
										@RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

			if (fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}

			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaTypeAceita ->
				mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel)
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		this.catalogoFotoProdutoService.excluir(restauranteId, produtoId);
	}


}
