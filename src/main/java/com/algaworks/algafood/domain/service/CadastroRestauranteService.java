package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CadastroRestauranteService {

	private final RestauranteRepository repository;

	private final CadastroCozinhaService cadastroCozinhaService;

	private final CadastroCidadeService cadastroCidadeService;

	private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

	private final CadastroUsuarioService cadastroUsuarioService;

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

	@Transactional
	public void ativacoes(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}

	@Transactional
	public void inativacoes(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}


	@Transactional
	public void abrir(Long restauranteId) {
		var restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
		var restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.fechar();
	}


	public Restaurante buscarOuFalhar(Long idRestaurante) {
		return this.repository.findById(idRestaurante).orElseThrow(() ->
				new RestauranteNaoEncontradoException(idRestaurante));
	}


	@Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
    }

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

		restaurante.associarResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

		restaurante.desassociarResponsavel(usuario);
	}
}
