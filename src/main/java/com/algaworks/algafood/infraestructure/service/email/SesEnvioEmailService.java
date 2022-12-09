package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.api.controller.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SesEnvioEmailService implements EnvioEmailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final EmailProperties emailProperties;
    private final Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String remetente = emailProperties.getRemetente();
            Set<String> destinatarios = mensagem.getDestinatarios();
            String assunto = mensagem.getAssunto();

            String corpo = processarTemplate(mensagem);

            SendEmailRequest sendEmailRequest =
                    new SendEmailRequest()
                            .withDestination(new Destination().withToAddresses(destinatarios))
                            .withMessage(
                                    new Message()
                                            .withBody(
                                                    new Body()
                                                            .withHtml(new Content().withCharset("UTF-8").withData(corpo)))
                                            .withSubject(new Content().withCharset("UTF-8").withData(assunto)))
                            .withSource(remetente);
            SendEmailResult result = amazonSimpleEmailService.sendEmail(sendEmailRequest);
            System.out.println(result.getMessageId());
        } catch (Exception ex) {
            throw new EmailException("Não foi possível enviar e-mail.", ex);
        }
    }

    private String processarTemplate(Mensagem mensagem)  {
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception ex) {
            throw new EmailException("Não foi possível montar o template.", ex);
        }
    }
}