package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    default void substituir(NovaFoto novaFoto, String nomeArquivoExistente) {
        this.armazenar(novaFoto);
        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }
    }


    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;
        private String contentType;

    }

    @Builder
    @Getter
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }

    }


}
