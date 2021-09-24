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


    private Set<Ncm> ncms;

    public abstract List<T> getItens();
    
    /**
     * 
     * A lista retornada não está persistida no DB
     * @return Lista de Ncms "criadas" com base nos itens da lista
     */
	public final Set<Ncm> getNcms() {
		if (this.ncms == null || this.ncms.isEmpty()) {
			this.ncms = new HashSet<>();
			for (Item item : itens) {
				this.ncms.add(new Ncm(item.getNcmId(), item.getClasseFiscal(), item.getExcecao()));
			}
		}
		return this.ncms;
	}

	
}
