package com.algaworks.algafood.api.controller.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SesEnvioEmailService;
import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.server.LDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final LDClient ldClient;

    @Bean
    public EnvioEmailService envioEmailService() {

        String emailFlag = ldClient.stringVariation("email-flag",
                LDContext.builder("example-user-key")
                                .name("Jorge").build(), "ses");

        switch (emailFlag) {
            case "fake":
                return new FakeEnvioEmailService();
            case "ses":
                return new SesEnvioEmailService();
            case "sandbox":
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }

}
