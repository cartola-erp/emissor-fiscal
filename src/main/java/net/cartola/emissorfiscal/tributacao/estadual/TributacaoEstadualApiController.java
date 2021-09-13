package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

/**
 * @date 10 de set. de 2021
 * @author robson.costa
 */
@RestController
@RequestMapping("api/v1/tributacao-estadual")
public class TributacaoEstadualApiController {
	
	private static final Logger LOG = Logger.getLogger(TributacaoEstadualApiController.class.getName());

	@Autowired
	private TributacaoEstadualService tribEstaService;
	
	@PostMapping("/gnre-aliquotas")
	public ResponseEntity<Response<GnreAliquotaDto>> findAliquotaByUfOrigUfDest(@RequestBody GnreAliquotaDto gnreAliquotaDto) {
		LOG.log(Level.INFO, "Buscando as alíquotas para a GNRE: {0} ", gnreAliquotaDto);
		/**
		 * TODO colocar algumas validações, Ex.: se tem todos os valores necessários (e válidos) para fazer a consulta
		 * 
		 * Caso não retornar a resposta HTTP correta
		 */
		Response<GnreAliquotaDto> response = new Response<GnreAliquotaDto>();
		Optional<GnreAliquotaDto> opGnreAliquota = tribEstaService.findGnreAliquota(gnreAliquotaDto);
		
		response.setData(opGnreAliquota.get());
		LOG.log(Level.INFO, "Alíquotas encontrado para a GNRE: {0} ", gnreAliquotaDto.getListGnreAliquota());
		return ResponseEntity.ok(response);
	}
		

}
