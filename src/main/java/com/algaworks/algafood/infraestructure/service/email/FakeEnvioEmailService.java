package com.algaworks.algafood.infraestructure.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FakeEnvioEmailService extends EnvioEmailServiceImpl {

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }


}
