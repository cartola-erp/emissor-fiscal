package net.cartola.emissorfiscal.documento;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.operacao.Operacao;

@Service
public class DocumentoFiscalService {

	@Autowired
	private DocumentoFiscalRepository documentoFiscalRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public DocumentoFiscalDto convertToDto(DocumentoFiscal docFiscal) {
		DocumentoFiscalDto docFiscalDto = modelMapper.map(docFiscal, DocumentoFiscalDto.class);
		return docFiscalDto;
	}
	
	public DocumentoFiscal convertToEntity(DocumentoFiscalDto docFiscalDto) throws ParseException {
		DocumentoFiscal docFiscal = modelMapper.map(docFiscalDto, DocumentoFiscal.class);
		if (docFiscalDto.getId() != null) {
			DocumentoFiscal oldDocFiscal = findOne(docFiscalDto.getId()).get();
			docFiscal.setId(oldDocFiscal.getId());
//			docFiscal.
		}
		return docFiscal;
	}
	
	public List<DocumentoFiscal> findAll() {
		return documentoFiscalRepository.findAll();
	}
	
	public Optional<DocumentoFiscal> save(DocumentoFiscal documentoFiscal) {
		return Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
	}
	
	public List<DocumentoFiscal> findDocumentoFiscalByOperacao(Operacao operacao) {
		return documentoFiscalRepository.findByOperacao(operacao);
	}
	
	public List<DocumentoFiscal> findDocumentoFiscalByVariasOperacoes(Collection<Operacao> operacoes) {
		return documentoFiscalRepository.findByOperacaoIn(operacoes);
	}
	
	public Optional<DocumentoFiscal> findOne(Long id) {
		return documentoFiscalRepository.findById(id);
	}

	public void deleteById(Long id) {
		documentoFiscalRepository.deleteById(id);
	}
	
	public List<DocumentoFiscal> findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(Long cnpjEmitente, String tipoDocumento, Long serie, Long numero) {
		return documentoFiscalRepository.findDocumentoFiscalByEmitenteCnpjAndTipoAndSerieAndNumero(cnpjEmitente,  tipoDocumento,  serie,  numero);
	}
}
