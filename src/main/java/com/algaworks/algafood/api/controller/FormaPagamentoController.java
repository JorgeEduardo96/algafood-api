package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FormaPagamentoController {

    private final FormaPagamentoRepository repository;

    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar() {
        return ResponseEntity.ok(this.formaPagamentoModelAssembler.toCollectionModel(this.repository.findAll()));
    }

    @GetMapping("/{idFormaPagamento}")
    public FormaPagamentoModel buscar(@PathVariable Long idFormaPagamento) {
        return this.formaPagamentoModelAssembler.toModel(this.cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        return this.formaPagamentoModelAssembler.toModel(this.cadastroFormaPagamentoService.salvar(
                this.formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput)));
    }

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

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idFormaPagamento) {
        this.cadastroFormaPagamentoService.excluir(idFormaPagamento);
    }


}
