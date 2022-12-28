package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.api.controller.core.email.EmailProperties;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SandboxEnvioEmailService extends EnvioEmailServiceImpl {

    private final EmailProperties emailProperties;
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

    @Override
    public SendEmailRequest criarSendEmailRequest(Mensagem mensagem) {
        String remetente = emailProperties.getRemetente();
        String destinatario = emailProperties.getSandBoxDestinatario();
        String assunto = mensagem.getAssunto();

        String corpo = processarTemplate(mensagem);

        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(destinatario))
                .withMessage(
                        new Message()
                                .withBody(
                                        new Body()
                                                .withHtml(new Content().withCharset("UTF-8").withData(corpo)))
                                .withSubject(new Content().withCharset("UTF-8").withData(assunto)))
                .withSource(remetente);
    }
}
