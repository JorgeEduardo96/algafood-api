package com.algaworks.algafood.core.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties("algafood.jwt.keystore")
@Getter
@Setter
public class JwtKeyStoreProperties {

    @NotNull
    private Resource jksLocation;
    @NotBlank
    private String keyStorePassword;
    @NotBlank
    private String keyPairAlias;

}
