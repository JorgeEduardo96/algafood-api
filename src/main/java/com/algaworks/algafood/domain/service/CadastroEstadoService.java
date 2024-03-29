package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroEstadoService {

	public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso!";
	private final EstadoRepository repository;

	@Transactional
	public Estado salvar(Estado estado) {
		return this.repository.save(estado);
	}

	@Transactional
	public void excluir(Long estadoid) {
		try {
			this.repository.deleteById(estadoid);
			this.repository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradoException(estadoid);
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoid));
		}
	}

	public Estado buscarOuFalhar(Long idEstado) {
		return this.repository.findById(idEstado).orElseThrow(() ->	new EstadoNaoEncontradoException(idEstado));
	}


}
