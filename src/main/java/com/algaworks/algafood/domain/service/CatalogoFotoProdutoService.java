package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogoFotoProdutoService {

    private final FotoStorageService fotoStorageService;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivos) {
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getRestauranteId(),
                foto.getProduto().getId());
        String nomeArquivoExistente = null;

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        foto = produtoRepository.saveAndFlush(foto);

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivos)
                .contentType(foto.getContentType())
                .build();

        fotoStorageService.substituir(novaFoto, nomeArquivoExistente);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return this.produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() ->
                new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);

        this.produtoRepository.deleteAndFlush(fotoProduto);
        this.fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

}
