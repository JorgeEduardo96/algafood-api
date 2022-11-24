package com.algaworks.algafood.unit;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CadastroCozinhaServiceUT {

    @InjectMocks
    private CadastroCozinhaService service;

    @Mock
    private CozinhaRepository repository;

    private final Cozinha cozinha = Cozinha.builder().build();

    @BeforeEach
    void init() {
        cozinha.setNome("Portuguesa");
    }

    @Test
    void testSaveCozinha_whenCozinhaGiven_returnsNewCozinha() {
        // Arrange
        Cozinha cozinhaCadastrada = Cozinha.builder().build();
        cozinhaCadastrada.setId(1L);
        cozinhaCadastrada.setNome("Portuguesa");
        when(repository.save(any())).thenReturn(cozinhaCadastrada);

        // Act
        service.salvar(cozinha);

        //Assert
        assertEquals(cozinhaCadastrada.getId(), 1L);
        assertEquals(cozinhaCadastrada.getNome(), cozinha.getNome());
        verify(repository).save(any());
    }

    @Test
    void testUpdateCozinha_whenCozinhaGiven_returnUpdatedCozinha() {
        // Arrange
        Cozinha cozinhaAntiga = Cozinha.builder().build();
        cozinhaAntiga.setNome("Portuguesa");
        cozinhaAntiga.setId(3L);

        Cozinha cozinhaAlterada = Cozinha.builder().build();
        cozinhaAlterada.setNome("Francesa");
        cozinhaAlterada.setId(3L);

        when(repository.save(cozinhaAntiga)).thenReturn(cozinhaAlterada);
        // Act
        service.salvar(cozinhaAntiga);

        //Assert
        assertEquals(cozinhaAlterada.getId(), 3L);
        assertEquals(cozinhaAlterada.getNome(), "Francesa");
        verify(repository).save(any());
    }

    @Test
    void testFindAllCozinhas_returnsCozinhas() {
        // Arrange
        Cozinha cozinha1 = Cozinha.builder().build();
        cozinha1.setId(1L);
        cozinha1.setNome("Tailandesa");

        Cozinha cozinha2 = Cozinha.builder().build();
        cozinha2.setId(2L);
        cozinha2.setNome("Francesa");

        when(repository.findAll()).thenReturn(List.of(cozinha1, cozinha2));

        // Act
        List<Cozinha> cozinhas = repository.findAll();

        // Assert
        assertThat(cozinhas).containsExactlyInAnyOrder(cozinha1, cozinha2);
    }

    @Test
    void testBuscarOuFalhar_whenCozinhaIdIs999_throwsCozinhaNaoEncontradaException() {
        // Arrange
        Long idCozinha = 999L;
        when(repository.findById(idCozinha)).thenReturn(Optional.empty());

        // Act & Assert
        CozinhaNaoEncontradaException ex = assertThrows(CozinhaNaoEncontradaException.class, () -> service.buscarOuFalhar(idCozinha),
                "Cozinha não encontrada deveria lançar CozinhaNaoEncontradaException.");

        assertThat(ex.getMessage().contains("Não existe um cadastro de cozinha com código"));
    }


}
