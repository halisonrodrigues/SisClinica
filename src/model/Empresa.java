package model;

import DAO.EmpresaMySQL;

public class Empresa {
	private Integer _id;
	private String nomeEmpresa;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String telefone;
	private String email;
	private String cnpj;
	private String inscricao_estadual;
//	private Image logoG;
//	private Image logoP;
	
	public Empresa(Integer _id, String nomeEmpresa, String endereco, String bairro, String cidade, String estado,
			String cep, String telefone, String email, String cnpj, String inscricao_estadual) {
		super();
		this._id = _id;
		this.nomeEmpresa = nomeEmpresa;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.telefone = telefone;
		this.email = email;
		this.cnpj = cnpj;
		this.inscricao_estadual = inscricao_estadual;
//		this.logoG = logoG;
//		this.logoP = logoP;
	}
	
	public Empresa(String nomeEmpresa, String endereco, String bairro, String cidade, String estado, String cep,
			String telefone, String email, String cnpj, String inscricao_estadual) {
		super();
		this.nomeEmpresa = nomeEmpresa;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.telefone = telefone;
		this.email = email;
		this.cnpj = cnpj;
		this.inscricao_estadual = inscricao_estadual;
//		this.logoG = logoG;
//		this.logoP = logoP;
	}
	
	public Integer get_id() {
		return _id;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getInscricao_estadual() {
		return inscricao_estadual;
	}
	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}
//	public Image getLogoG() {
//		return logoG;
//	}
//	public void setLogoG(Image logoG) {
//		this.logoG = logoG;
//	}
//	public Image getLogoP() {
//		return logoP;
//	}
//	public void setLogoP(Image logoP) {
//		this.logoP = logoP;
//	}
	
	//------------------------DAO-------------------------
	
	private static EmpresaMySQL dao = new EmpresaMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.find() != null) ){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	
	public static Empresa find(){
		return dao.find();
	}
}
