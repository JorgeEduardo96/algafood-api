package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroEstadoService {

	public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso!";
	private final EstadoRepository repository;

	public Estado salvar(Estado estado) {
		return this.repository.save(estado);
	}

	public void excluir(Long estadoid) {
		try {
			this.repository.deleteById(estadoid);
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
