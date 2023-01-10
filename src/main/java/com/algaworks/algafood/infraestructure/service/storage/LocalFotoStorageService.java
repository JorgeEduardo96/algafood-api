package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(getArquivoPath(nomeArquivo)))
                    .build();
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
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquvo));
    }
}
