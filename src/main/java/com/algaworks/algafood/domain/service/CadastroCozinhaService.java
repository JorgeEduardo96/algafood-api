package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso!";
	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return this.repository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			this.repository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException ex) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return this.repository.findById(cozinhaId).orElseThrow(() ->
				new CozinhaNaoEncontradaException(cozinhaId));
	}


}
