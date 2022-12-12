package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.api.controller.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Set;

public class SesEnvioEmailService implements EnvioEmailService {

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            SendEmailRequest sendEmailRequest = criarSendEmailRequest(mensagem);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        } catch (Exception ex) {
            throw new EmailException("Não foi possível enviar e-mail.", ex);
        }
    }

    protected SendEmailRequest criarSendEmailRequest(Mensagem mensagem) {
        String remetente = emailProperties.getRemetente();
        Set<String> destinatarios = mensagem.getDestinatarios();
        String assunto = mensagem.getAssunto();

        String corpo = processarTemplate(mensagem);

        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(destinatarios))
                .withMessage(
                        new Message()
                                .withBody(
                                        new Body()
                                                .withHtml(new Content().withCharset("UTF-8").withData(corpo)))
                                .withSubject(new Content().withCharset("UTF-8").withData(assunto)))
                .withSource(remetente);
    }

    protected String processarTemplate(Mensagem mensagem)  {
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception ex) {
            throw new EmailException("Não foi possível montar o template.", ex);
        }
    }
}
