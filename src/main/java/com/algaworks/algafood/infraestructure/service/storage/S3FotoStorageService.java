package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoNovaFoto(nomeArquivo);

            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();
        } catch (Exception ex) {
            throw new StorageException("Não foi possível recuperar arquivo na Amazon S3", ex);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoNovaFoto(novaFoto.getNomeArquivo());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", ex);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoNovaFoto(nomeArquivo);

            var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
                    caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível remover o arquivo na Amazon S3", ex);
        }
    }

    private String getCaminhoNovaFoto(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
