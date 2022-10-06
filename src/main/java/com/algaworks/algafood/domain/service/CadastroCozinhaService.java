package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroCozinhaService {

	public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso!";
	private final CozinhaRepository repository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return this.repository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			this.repository.deleteById(cozinhaId);
			this.repository.flush();
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
