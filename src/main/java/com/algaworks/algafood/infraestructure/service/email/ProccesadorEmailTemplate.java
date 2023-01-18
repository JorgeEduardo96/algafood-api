package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
@RequiredArgsConstructor
public class ProccesadorEmailTemplate {

    private final Configuration freemarkerConfig;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem)  {
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception ex) {
            throw new EmailException("Não foi possível montar o template.", ex);
        }
    }

}
