//package net.cartola.emissorfiscal.pessoa;
//
//public class Pessoa extends RegistroBase{
//	private static final long serialVersionUID = 1324081234931L;
//    private Long id;
//    private CadastroTipo cadastroTipo = CadastroTipo.PROSPECT;
//    private PessoaGrupo pessoaGrupo;
//    private PessoaTipo pessoaTipo = PessoaTipo.FISICA;
//    private String nomeRazaoSocial;
//    private String apelidoNomeFantasia;
//    private Date nascimentoFudacao;
//    private String obs;
//    private boolean cliente = true;
//    private boolean fornecedor = false;
//    private boolean anfitriao = false;
//    private boolean considerarFaturamentoMinimo = true;
//    private boolean ativo = true;
//    private List<PessoaEndereco> enderecos;
//    private List<PessoaContato> contatos;
//    private List<PessoaTelefone> telefones;
//    private List<PessoaEMail> emails;
//
//    ////@Id
//    ////@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pess_id")
//    ////@SequenceGenerator(name = "seq_pess_id", sequenceName = "seq_pess_id", allocationSize = 1)
//    ////@Column(name = "pess_id")
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    ////@Enumerated(EnumType.STRING)
//    ////@Column(name = "cada_tipo", nullable = false)
//    public CadastroTipo getCadastroTipo() {
//        return cadastroTipo;
//    }
//
//    public void setCadastroTipo(CadastroTipo cadastroTipo) {
//        this.cadastroTipo = cadastroTipo;
//    }
//
//    /**
//     * Grupo empresarial da pessoa, caso a ela perten�a a algum
//     * ////@return
//     */
//    ////@ManyToOne
//    ////@JoinColumn(name="pess_grupo_id", nullable = true)
//    public PessoaGrupo getPessoaGrupo() {
//        return pessoaGrupo;
//    }
//
//    public void setPessoaGrupo(PessoaGrupo pessoaGrupo) {
//        this.pessoaGrupo = pessoaGrupo;
//    }
//
//    ////@Enumerated(EnumType.STRING)
//    ////@Column(name = "pess_tipo", length = 45, nullable = false, 
//    //columnDefinition = "character varying(45) default 'FISICA'")
//    public PessoaTipo getPessoaTipo() {
//        return pessoaTipo;
//    }
//
//    public void setPessoaTipo(PessoaTipo pessoaTipo) {
//        this.pessoaTipo = pessoaTipo;
//    }
//
//    ////@Column(name = "nome_raza_soci", nullable = false, length = 255)
////    //////@NotNull(message = "nome n�o pode ser vazio") a valida//o //////@Size torna esta desnecess�ria
//    //////@Size(min=5,max=255, message = "Nome ou raz�o social '${validatedValue}' inv�lido, deve conter entre {min} e {max} caracteres.")
//    public String getNomeRazaoSocial() {
//        return nomeRazaoSocial;
//    }
//
//    public void setNomeRazaoSocial(String nomeRazaoSocial) {
//        this.nomeRazaoSocial = nomeRazaoSocial;
//    }
//
////    ////@Column(name = "apel_nome_fant", nullable = false, length = 255)
////    //////@NotNull(message = "apelido ou nome fantasia n�o pode ser vazio")
////    //////@Size(min=5,max=255, message = "tamanho do apelido ou nome fantasia '${validatedValue}' deve conter entre {min} e {max} caracteres")
//    
////  as valida//es foram removidas levando em considera//o o cadastro de pessoa PROSPECT, as valuda//es foram movidas para
////  a classe PessoaCadastroLogic no metodo validacoes
//    ////@Column(name = "apel_nome_fant", length = 255)
//    public String getApelidoNomeFantasia() {
//        return apelidoNomeFantasia;
//    }
//
//    public void setApelidoNomeFantasia(String apelidoNomeFantasia) {
//        this.apelidoNomeFantasia = apelidoNomeFantasia;
//    }
//
//    ////@Past(message = "A data de nascimento deve ser anterior a atual.")
//    ////@Temporal(TemporalType.DATE)
//    ////@Column(name="nasc_fund")
//    public Date getNascimentoFudacao() {
//        return nascimentoFudacao;
//    }
//
//    public void setNascimentoFudacao(Date nascimentoFudacao) {
//        this.nascimentoFudacao = nascimentoFudacao;
//    }
//    
//    ////@Transient
//    public String getNascimentoFudacaoString() {
//        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
//        if (this.nascimentoFudacao == null) {
//            return "";
//        } else {
//            return dataFormat.format(this.nascimentoFudacao);
//        }
//    }
//
//    
//    ////@Lob
//    ////@Column(name = "obs", length = 255)
//    public String getObs() {
//        return obs;
//    }
//
//    public void setObs(String obs) {
//        this.obs = obs;
//    }
//
//    ////@Column(name="clie")
//    public boolean getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(boolean cliente) {
//        this.cliente = cliente;
//    }
//
//    ////@Column(name="forn")
//    public boolean getFornecedor() {
//        return fornecedor;
//    }
//
//    public void setFornecedor(boolean fornecedor) {
//        this.fornecedor = fornecedor;
//    }
//
//    ////@Column(name="anfi")
//    public boolean getAnfitriao() {
//        return anfitriao;
//    }
//
//    public void setAnfitriao(boolean anfitriao) {
//        this.anfitriao = anfitriao;
//    }
//
//    ////@Column(name="cons_fat_min", nullable = false)
//    public boolean isConsiderarFaturamentoMinimo() {
//        return considerarFaturamentoMinimo;
//    }
//
//    public void setConsiderarFaturamentoMinimo(boolean considerarFaturamentoMinimo) {
//        this.considerarFaturamentoMinimo = considerarFaturamentoMinimo;
//    }
//
//    ////@Column(name="ativ", nullable = false)
//    public boolean isAtivo() {
//        return ativo;
//    }
//
//    public void setAtivo(boolean ativo) {
//        this.ativo = ativo;
//    }
//    
//    public boolean add(PessoaEndereco endereco) {
//        if (this.enderecos == null) {
//            this.enderecos = new ArrayList<>();
//        }
//        endereco.setPessoa(this);
//        return this.enderecos.add(endereco);
//    }
//    
//    public boolean add(PessoaContato contato) {
//        if (this.contatos == null) {
//            this.contatos = new ArrayList<>();
//        }
//        contato.setPessoa(this);
//        return this.contatos.add(contato);
//    }
//    
//    public boolean add(PessoaTelefone telefone) {
//        if (this.telefones == null) {
//            this.telefones = new ArrayList<>();
//        }
//        telefone.setPessoa(this);
//        return this.telefones.add(telefone);
//    }
//
//    public boolean add(PessoaEMail email) {
//        if (this.emails == null) {
//            this.emails = new ArrayList<>();
//        }
//        email.setPessoa(this);
//        return this.emails.add(email);
//    }
//    
//    ////@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
//    public List<PessoaEndereco> getEnderecos() {
//        if(this.enderecos == null){
//            enderecos = new ArrayList<>();
//        }
//        return enderecos;
//    }
//
//    public void setEnderecos(List<PessoaEndereco> enderecos) {
//        this.enderecos = enderecos;
//    }
//    
//    ////@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
//    public List<PessoaContato> getContatos() {
//        if(this.contatos == null){
//            contatos = new ArrayList<>();
//        }
//        return contatos;
//    }
//
//    public void setContatos(List<PessoaContato> contatos) {
//        this.contatos = contatos;
//    }
//    
//    ////@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
//    public List<PessoaTelefone> getTelefones() {
//        if(this.telefones == null){
//            telefones = new ArrayList<>();
//        }
//        return telefones;
//    }
//
//    public void setTelefones(List<PessoaTelefone> telefones) {
//        this.telefones = telefones;
//    }
//
//    ////@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
//    public List<PessoaEMail> getEmails() {
//        if(this.emails == null){
//            emails = new ArrayList<>();
//        }
//        return emails;
//    }
//
//    public void setEmails(List<PessoaEMail> emails) {
//        this.emails = emails;
//    }
//
//    ////@Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    ////@Override
//    public boolean equals(Object object) {
//        if (!(object instanceof Pessoa)) {
//            return false;
//        }
//        Pessoa other = (Pessoa) object;
//        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
//    }
//
//    ////@Override
//    public String toString() {
//        return "Pessoa[id=" + id + ", nome=\"" + this.nomeRazaoSocial + "\"]";
//    }
//    
//    ////@Override
//    public Pessoa clone() throws CloneNotSupportedException {
//        Pessoa clone = (Pessoa) super.clone();
//        clone.cadastroTipo = this.cadastroTipo;
//        if (this.pessoaGrupo != null) {
//            clone.pessoaGrupo = this.pessoaGrupo.clone();
//        }
//        clone.pessoaTipo = this.pessoaTipo;
//        clone.nomeRazaoSocial = this.nomeRazaoSocial;
//        clone.apelidoNomeFantasia = this.apelidoNomeFantasia;
//        clone.nascimentoFudacao = this.nascimentoFudacao;
//        clone.obs = this.obs;
//        clone.cliente = this.cliente;
//        clone.fornecedor = this.fornecedor;
//        clone.anfitriao = this.anfitriao;
//        clone.considerarFaturamentoMinimo = this.considerarFaturamentoMinimo;
//        clone.ativo = this.ativo;
//
//        if (this.enderecos != null) {
//            List<PessoaEndereco> dlist = new ArrayList<>();
//            for (PessoaEndereco oo : this.enderecos) {
//                dlist.add(oo.clone());
//            }
//            clone.enderecos = dlist;
//        }
//
//        if (this.contatos != null) {
//            List<PessoaContato> dlist = new ArrayList<>();
//            for (PessoaContato oo : this.contatos) {
//                dlist.add(oo.clone());
//            }
//            clone.contatos = dlist;
//        }
//        if (this.telefones != null) {
//            List<PessoaTelefone> dlist = new ArrayList<>();
//            for (PessoaTelefone oo : this.telefones) {
//                dlist.add(oo.clone());
//            }
//            clone.telefones = dlist;
//        }
//
//        if (this.emails != null) {
//            List<PessoaEMail> dlist = new ArrayList<>();
//            for (PessoaEMail oo : this.emails) {
//                dlist.add(oo.clone());
//            }
//            clone.emails = dlist;
//        }
//
//        return clone;
//    }
//
//}
