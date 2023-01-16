package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido Cancelado")
                .corpo("emails/pedido-cancelado.html")
                .variavel("pedido", event.getPedido())
                .destinatario(event.getPedido().getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

}
