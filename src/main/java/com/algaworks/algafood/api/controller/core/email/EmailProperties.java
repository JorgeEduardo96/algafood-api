package com.algaworks.algafood.api.controller.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    @NotNull
    private String featureFlagKey;

    private TipoEnvioEmail impl = TipoEnvioEmail.FAKE;

    @NotNull
    private String sandBoxDestinatario;

    public enum TipoEnvioEmail {

        SES("ses"), FAKE("fake"), SANDBOX("sandbox");

        private String label;

        TipoEnvioEmail(String label) {
            this.label = label;
        }
    }

}
