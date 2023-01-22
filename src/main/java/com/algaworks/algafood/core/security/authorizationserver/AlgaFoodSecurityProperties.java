package com.algaworks.algafood.core.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties("algafood.auth")
@Getter
@Setter
@Validated
public class AlgaFoodSecurityProperties {

    @NotBlank
    private String providerUrl;

}
