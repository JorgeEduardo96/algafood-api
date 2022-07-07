package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = this.cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);
		return this.repository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long idRestaurante) {
		return this.repository.findById(idRestaurante).orElseThrow(() ->
				new RestauranteNaoEncontradoException(idRestaurante));
	}


}
