package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            return Files.newInputStream(getArquivoPath(nomeArquivo));
        } catch (Exception ex) {
            throw new StorageException("Não foi póssível recuperar arquivo.", ex);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception ex) {
            throw new StorageException("Não foi póssível aramazenar arquivo.", ex);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception ex) {
            throw new StorageException("Não foi póssível exclusão arquivo.", ex);
        }
    }

    private Path getArquivoPath(String nomeArquvo) {
        return diretorioFotos.resolve(Path.of(nomeArquvo));
    }
}
