package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.BuscaPedidoService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infraestructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoRepository repository;

    private final BuscaPedidoService buscaPedidoService;

    private final PedidoModelAssembler assembler;

    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    private final PedidoInputDisassembler disassembler;

    private final EmissaoPedidoService emissaoPedidoService;

    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    private final AlgaSecurity algaSecurity;

    @CheckSecurity.Pedidos.PodePesquisar
    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
//        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = repository.findAll(PedidoSpecs.usandoFiltro(filtro),
                pageable);

        return pagedResourcesAssembler
                .toModel(new PageWrapper<>(pedidosPage, pageable), pedidoResumoModelAssembler);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return assembler.toModel(buscaPedidoService.buscarOuFalhar(codigoPedido));
    }

    @CheckSecurity.Pedidos.PodeCriar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = disassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(Usuario.builder().id(algaSecurity.getUsuarioId()).build());

            return assembler.toModel(emissaoPedidoService.emissaoPedido(novoPedido));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

//    private Pageable traduzirPageable(Pageable apiPageable) {
//        var mapeamento = ImmutableMap.of(
//                "codigo", "codigo,",
//                "subTotal", "subTotal",
//                "restaurante.nome", "restaurante.nome",
//                "cliente.nome", "cliente.nome",
//                "valorTotal", "valorTotal"
//        );
//
//        return PageableTranslator.translate(apiPageable, mapeamento);
//    }


}
