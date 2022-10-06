package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroRestauranteService {

	private final RestauranteRepository repository;

	private final CadastroCozinhaService cadastroCozinhaService;

	private final CadastroCidadeService cadastroCidadeService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = this.cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = this.cadastroCidadeService.buscarOuFalhar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return this.repository.save(restaurante);
	}

	@Transactional
	public void ativar(Long restauranteId) {
		var restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {
		var restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();
	}


	public Restaurante buscarOuFalhar(Long idRestaurante) {
		return this.repository.findById(idRestaurante).orElseThrow(() ->
				new RestauranteNaoEncontradoException(idRestaurante));
	}


}
