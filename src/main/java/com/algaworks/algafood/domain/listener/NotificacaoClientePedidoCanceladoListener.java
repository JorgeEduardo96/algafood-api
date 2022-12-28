package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.api.controller.core.email.EmailProperties;
import com.algaworks.algafood.api.controller.core.launchdarkly.LaunchDarklyClient;
import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SesEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.algaworks.algafood.api.controller.core.email.EmailProperties.TipoEnvioEmail;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final FakeEnvioEmailService fakeEnvioEmailService;
    private final SandboxEnvioEmailService sandboxEnvioEmailService;
    private final SesEnvioEmailService envioEmailService;

    private final LaunchDarklyClient launchDarklyClient;

    private final EmailProperties emailProperties;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        if (launchDarklyClient.getFeatureFlagBooleanValue(emailProperties.getFeatureFlagKey())) {
            TipoEnvioEmail flagEmail =
                    TipoEnvioEmail
                            .valueOf(launchDarklyClient.getFeatureFlagStringValue("email-flag").toUpperCase());

            var mensagem = EnvioEmailService.Mensagem.builder()
                    .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido Cancelado")
                    .corpo("pedido-cancelado.html")
                    .variavel("pedido", event.getPedido())
                    .destinatario(event.getPedido().getCliente().getEmail())
                    .build();

            switch (flagEmail) {
                case SES: {
                    envioEmailService.enviar(mensagem);
                    break;
                }
                case SANDBOX: {
                    sandboxEnvioEmailService.enviar(mensagem);
                    break;
                }
                default: {
                    fakeEnvioEmailService.enviar(mensagem);
                    break;
                }
            }
        }
    }

}
