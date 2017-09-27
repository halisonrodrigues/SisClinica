package model;

import java.util.Date;
import java.util.List;

import DAO.MedicoMySQL;

public class Medico {
	
	private Integer _id;
	private String nome;
	private Integer idEspecialidade;
	private String crm;
	private String cpf;
	private Date dataCadastro;
	
	public Medico(Integer _id, String nome, Integer idEspecialidade, String crm, String cpf, Date dataCadastro) {
		super();
		this._id = _id;
		this.nome = nome;
		this.idEspecialidade = idEspecialidade;
		this.crm = crm;
		this.cpf = cpf;
		this.dataCadastro = dataCadastro;
	}

	public Medico(String nome, Integer idEspecialidade, String crm, String cpf, Date dataCadastro) {
		super();
		this.nome = nome;
		this.idEspecialidade = idEspecialidade;
		this.crm = crm;
		this.cpf = cpf;
		this.dataCadastro = dataCadastro;
	}

	public Integer getId() {
		return _id;
	}
	public void setId(Integer id) {
		this._id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdEspecialidade() {
		return idEspecialidade;
	}
	public void setIdEspecialidade(Integer idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	// ------------------------ DAO ---------------------
	private static MedicoMySQL dao = new MedicoMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.findById(_id) != null)){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	
	public static List<Medico> all(){
		return dao.all();
	}
	
	public static List<Medico> allNome(String nome){
		return dao.allByNome(nome);
	}
	
	public static Medico findById(int pk){
		return dao.findById(pk);
	}
	
	public static Medico findByName(String nome){
		return dao.findByName(nome);
	}
	
	public static List<Medico> findByEspecialidade(String nomeEspec){
		return dao.allByEspecialidade(nomeEspec);
	}
	
	public static String findNomeEspecialidade(int idEspec){
		return dao.nomeEspecialidade(idEspec);
	}
}
