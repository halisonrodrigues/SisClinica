package model;

import java.util.Date;
import java.util.List;

import DAO.UsuarioMySQL;

public class Usuario {
	private Integer _id;
	private String nome;
	private String cpf;
	private String status;
 	private String usuario;
	private String senha;
	private String nívelAcesso;
	private Date dataCadastro;
	
	
	public Usuario(Integer _id, String nome, String cpf, String usuario, String senha, String nívelAcesso,
			String status, Date dataCadastro) {
		super();
		this._id = _id;
		this.nome = nome;
		this.cpf = cpf;
		this.status = status;
		this.usuario = usuario;
		this.senha = senha;
		this.nívelAcesso = nívelAcesso;
		this.dataCadastro = dataCadastro;
	}
	
	public Usuario(String nome, String cpf, String usuario, String senha, String nívelAcesso, String status,
			Date dataCadastro) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.status = status;
		this.usuario = usuario;
		this.senha = senha;
		this.nívelAcesso = nívelAcesso;
		this.dataCadastro = dataCadastro;
	}

	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNívelAcesso() {
		return nívelAcesso;
	}
	public void setNívelAcesso(String nívelAcesso) {
		this.nívelAcesso = nívelAcesso;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	//----------------- DAO --------------------
	private static UsuarioMySQL dao = new UsuarioMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.find(_id) != null) ){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	
	public static List<Usuario> all(){
		return dao.all();
	}
	
	public static List<Usuario> allNivel(String nivel){
		return dao.all(nivel);
	}
	
	public static Usuario find(int pk){
		return dao.find(pk);
	}
	
	public static Usuario findUsuario(String usuario){
		return dao.find(usuario);
	}
}
