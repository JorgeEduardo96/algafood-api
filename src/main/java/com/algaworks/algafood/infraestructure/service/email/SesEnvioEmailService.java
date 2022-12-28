package com.algaworks.algafood.infraestructure.service.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SesEnvioEmailService extends EnvioEmailServiceImpl {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            SendEmailRequest sendEmailRequest = criarSendEmailRequest(mensagem);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        } catch (Exception ex) {
            throw new EmailException("Não foi possível enviar e-mail.", ex);
        }
    }

}
