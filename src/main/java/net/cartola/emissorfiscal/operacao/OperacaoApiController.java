package net.cartola.emissorfiscal.operacao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

/**
 * @date 25 de mar. de 2021
 * @author robson.costa
 */
@RestController
@RequestMapping("/api/v1/operacao")
public class OperacaoApiController {

    private static final Logger LOG = Logger.getLogger(OperacaoApiController.class.getName());

    @Autowired
    private OperacaoService operacaoService;


    @PostMapping()
    public ResponseEntity<Response<Operacao>> save(@Valid @RequestBody Operacao operacao) {
        LOG.log(Level.INFO, "Salvando uma nova operacao {0} " ,operacao);
		Response<Operacao> response = new Response<>();

        Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(operacao.getDescricao());
	
        if(opOperacao.isPresent()) {
        	response.setData(opOperacao.get());
        	return ResponseEntity.ok(response);
        } else {
        	Optional<Operacao> opOperacaoSaved = operacaoService.save(operacao);
            LOG.log(Level.INFO, "Operacao salva {0} " ,opOperacaoSaved);
        	response.setData(opOperacaoSaved.get());
        	return ResponseEntity.ok(response);
        }
    }
    
    @PutMapping() 
    public ResponseEntity<Response<Operacao>> update(@Valid @RequestBody Operacao operacao) {
        LOG.log(Level.INFO, "Atualizando a operacao {0} " ,operacao);
 		Response<Operacao> response = new Response<>();
 		
        Optional<Operacao> opOperacao = operacaoService.save(operacao);

        if (!opOperacao.isPresent()) {
        	List<String> listError = Arrays.asList("Operação não encontrada no EMISSOR-FISCAL");
        	response.setErrors(listError);
        	return ResponseEntity.noContent().build();
        } else {
            LOG.log(Level.INFO, "Operacao atualizada {0} " ,opOperacao);
        	response.setData(opOperacao.get());
        	return ResponseEntity.ok(response);
        }
    }

}
