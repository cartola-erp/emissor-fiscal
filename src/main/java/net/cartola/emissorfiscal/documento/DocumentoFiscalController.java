package net.cartola.emissorfiscal.documento;

import net.cartola.emissorfiscal.operacao.Operacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/doc-fiscal")
public class DocumentoFiscalController {

    @Autowired
    private DocumentoFiscalRepository documentoFiscalRepository;
    @Autowired
    private DocumentoFiscalService documentoFiscalService;

    //Consulta de saidas
    @GetMapping("saida/consulta")
    public ModelAndView lodSaidas(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "chave", required = false) String nfeChaveAcesso
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("emissao").descending());

        IndicadorDeOperacao tipoOperacao = IndicadorDeOperacao.SAIDA;

        Page<DocumentoFiscal> fiscalPage;
        if(nfeChaveAcesso != null && !nfeChaveAcesso.isEmpty()){

            fiscalPage = documentoFiscalService.buscarPorTipoDeOperacaoEChave(nfeChaveAcesso, tipoOperacao, pageable);
        } else{

            fiscalPage = documentoFiscalService.buscarPorTipoDeOperacao(tipoOperacao, pageable);
        }

        int totalPages = fiscalPage.getTotalPages();
        int startPage = Math.max(fiscalPage.getNumber() - 2, 0);
        int endPage = Math.min(fiscalPage.getNumber() + 2, totalPages - 1);

        ModelAndView modelAndView = new ModelAndView("docFiscal/docFiscalSaida");
        modelAndView.addObject("fiscalPage", fiscalPage);
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);

        return modelAndView;
    }

    //Consulta de entradas
    @GetMapping("entrada/consulta")
    public ModelAndView loadEntradas(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "chave", required = false) String nfeChaveAcesso
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("cadastro").descending());
        IndicadorDeOperacao tipoOperacao = IndicadorDeOperacao.ENTRADA;

        Page<DocumentoFiscal> fiscalPage;
        if(nfeChaveAcesso != null && !nfeChaveAcesso.isEmpty()){

            fiscalPage = documentoFiscalService.buscarPorTipoDeOperacaoEChave(nfeChaveAcesso, tipoOperacao, pageable);
        }else{
            fiscalPage = documentoFiscalService.buscarPorTipoDeOperacao(tipoOperacao, pageable);
        }

        int totalPages = fiscalPage.getTotalPages();
        int startPage = Math.max(fiscalPage.getNumber() - 2, 0);
        int endPage = Math.min(fiscalPage.getNumber() + 2, totalPages - 1);

        ModelAndView modelAndView = new ModelAndView("docFiscal/docFiscalEntrada");
        modelAndView.addObject("fiscalPage", fiscalPage);
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);

        return modelAndView;
    }
}
