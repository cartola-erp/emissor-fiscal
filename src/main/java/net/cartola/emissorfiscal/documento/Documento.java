package net.cartola.emissorfiscal.documento;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;

/**
 * 
 * Essa classe contém os atributos em comum de DocumentoFiscal e Devolucao;
 * 
 * @date 21 de set. de 2021
 * @author robson.costa
 */
public abstract class Documento<T extends Item> {
	
    protected Operacao operacao;
    protected Loja loja;
    protected Pessoa emitente;
    protected Pessoa destinatario;
    protected List<T> itens;

    protected LocalDateTime cadastro;
    protected String criadoPor;
    protected LocalDateTime alterado;
    protected String alteradoPor;

    // Para não ficar indo no DB, será montado um objeto do tipo Ncm
    private Set<Ncm> ncms;


    public abstract Operacao getOperacao();
    public abstract void setOperacao(Operacao operacao);
    
    public abstract Loja getLoja();
    public abstract void setLoja(Loja loja);
    
    public abstract Pessoa getEmitente();
    public abstract void setEmitente(Pessoa emitente);
    
    public abstract Pessoa getDestinatario();
    public abstract void setDestinatario(Pessoa destinatario);
    
    public abstract List<T> getItens();
    
    /**
     * 
     * A lista retornada não está persistida no DB
     * @return Lista de Ncms "criadas" com base nos itens da lista
     */
	public final Set<Ncm> getNcms() {
		if (this.ncms == null) {
			this.ncms = new HashSet<>();
		}
		if (this.ncms.isEmpty() || (getItens() !=null && getItens().size() > this.ncms.size())) {
			for (Item item : getItens()) {
				this.ncms.add(item.getNcm());
			}
		}
		return this.ncms;
	}

	
}
