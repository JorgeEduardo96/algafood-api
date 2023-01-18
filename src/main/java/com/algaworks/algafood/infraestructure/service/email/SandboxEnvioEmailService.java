package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;

public class SandboxEnvioEmailService extends SesEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private ProccesadorEmailTemplate proccesadorEmailTemplate;

    @Override
    protected SendEmailRequest criarSendEmailRequest(Mensagem mensagem) {
        String remetente = emailProperties.getRemetente();
        String destinatario = emailProperties.getSandBoxDestinatario();
        String assunto = mensagem.getAssunto();

        String corpo = proccesadorEmailTemplate.processarTemplate(mensagem);

        SendEmailRequest sendEmailRequest =
                new SendEmailRequest()
                        .withDestination(new Destination().withToAddresses(destinatario))
                        .withMessage(
                                new Message()
                                        .withBody(
                                                new Body()
                                                        .withHtml(new Content().withCharset("UTF-8").withData(corpo)))
                                        .withSubject(new Content().withCharset("UTF-8").withData(assunto)))
                        .withSource(remetente);
        return sendEmailRequest;
    }
}
