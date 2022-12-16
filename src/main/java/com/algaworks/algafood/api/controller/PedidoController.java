package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.controller.core.data.PageableTranslator;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.BuscaPedidoService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infraestructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoRepository repository;

    private final BuscaPedidoService buscaPedidoService;

    private final PedidoModelAssembler assembler;

    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    private final PedidoInputDisassembler disassembler;

    private final EmissaoPedidoService emissaoPedidoService;

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping
    public Page<PedidoResumoModel> filtrar(PedidoFilter filtro, Pageable pageable) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidos = repository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoModel> pedidosResumo = pedidoResumoModelAssembler.toCollectionModel(pedidos.getContent());

        return new PageImpl<>(pedidosResumo, pageable, pedidos.getTotalElements());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping("/{codigo}")
    public PedidoModel buscar(@PathVariable String codigo) {
        return assembler.toModel(buscaPedidoService.buscarOuFalhar(codigo));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = disassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(Usuario.builder().id(1L).build());

            return assembler.toModel(emissaoPedidoService.emissaoPedido(novoPedido));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo,",
                "subTotal", "subTotal",
                "restaurante.nome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }


}
