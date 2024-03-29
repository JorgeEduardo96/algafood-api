package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private final FormaPagamentoRepository repository;

    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        OffsetDateTime dataUltimaAtualizacao = repository.getDataUltimaAtualizacao();

        String eTag = dataUltimaAtualizacao != null ? String.valueOf(dataUltimaAtualizacao.toEpochSecond()) : "0";

        if (request.checkNotModified(eTag)) return null;

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(this.formaPagamentoModelAssembler.toCollectionModel(this.repository.findAll()));
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping("/{idFormaPagamento}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long idFormaPagamento, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);

        String eTag = formaPagamento.getDataAtualizacao() != null ?
                String.valueOf(formaPagamento.getDataAtualizacao().toEpochSecond()) : "0";

        if (request.checkNotModified(eTag)) return null;

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModelAssembler.toModel(formaPagamento));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        return this.formaPagamentoModelAssembler.toModel(this.cadastroFormaPagamentoService.salvar(
                this.formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput)));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{idFormaPagamento}")
    public FormaPagamentoModel atualizar(@PathVariable Long idFormaPagamento, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = this.cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);
        this.formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        try {
            return this.formaPagamentoModelAssembler.toModel(this.cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
        } catch (FormaPagamentoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idFormaPagamento) {
        this.cadastroFormaPagamentoService.excluir(idFormaPagamento);
    }


}
