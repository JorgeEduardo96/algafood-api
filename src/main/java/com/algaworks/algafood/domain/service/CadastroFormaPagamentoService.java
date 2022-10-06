package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de código %d não pode ser removida, pois está em uso";

    private final FormaPagamentoRepository repository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return this.repository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            this.repository.deleteById(formaPagamentoId);
            this.repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }

    public FormaPagamento buscarOuFalhar(Long idFormaPagamento) {
        return this.repository.findById(idFormaPagamento).orElseThrow(()
                -> new FormaPagamentoNaoEncontradaException(idFormaPagamento));
    }

}
