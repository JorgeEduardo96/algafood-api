package com.algaworks.algafood.unit;

import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastroRestauranteServiceUT {

    @InjectMocks
    private CadastroRestauranteService service;

    @Mock
    private RestauranteRepository repository;

    @Mock
    private CadastroCozinhaService cozinhaService;

    @Mock
    private CadastroCidadeService cidadeService;


    @Test
    void testSalvarRestaurante_returnsNewRestaurante() {
        // Arrange
        Restaurante restaurante = Restaurante.builder()
                .id(1L)
                .nome("Boteco da Maria")
                .taxaFrete(new BigDecimal("3.75"))
                .cozinha(Cozinha.builder()
                        .id(1L)
                        .nome("Brasileira")
                        .build())
                .endereco(Endereco.builder()
                        .logradouro("Rua dos Anjos")
                        .numero("111")
                        .bairro("Rosarinho")
                        .cep("52041-300")
                        .complemento("Casa")
                        .cidade(Cidade.builder()
                                .id(1L)
                                .nome("Recife")
                                .estado(Estado.builder()
                                        .id(1L)
                                        .nome("Pernambuco")
                                        .build())
                                .build())
                        .build())
                .build();

        when(cozinhaService.buscarOuFalhar(1L)).thenReturn(restaurante.getCozinha());
        when(cidadeService.buscarOuFalhar(1L)).thenReturn(restaurante.getEndereco().getCidade());
        when(repository.save(any())).thenReturn(restaurante);

        // Act
        service.salvar(restaurante);

        verify(cozinhaService, times(1)).buscarOuFalhar(1L);
        verify(cidadeService, times(1)).buscarOuFalhar(1L);
        verify(repository, times(1)).save(any());

    }

}
