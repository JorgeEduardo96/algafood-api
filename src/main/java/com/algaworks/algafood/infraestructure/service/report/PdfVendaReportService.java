package com.algaworks.algafood.infraestructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PdfVendaReportService implements VendaReportService {

    private final VendaQueryService vendaQueryService;

    private final String REPORT_PATH = "/relatorios/vendas-diarias.jasper";

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {

            var inputStream = this.getClass().getResourceAsStream(REPORT_PATH);

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var datasource = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, datasource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Não foi possivel emitir o relatório de Vendas Diárias", e);
        }
    }
}
