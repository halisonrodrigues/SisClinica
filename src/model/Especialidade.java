package model;

import java.util.List;

import DAO.EspecialidadeMySQL;

public class Especialidade {
	
	private Integer _id;
	private String nome;
	
	public Especialidade(Integer _id, String nome) {
		super();
		this._id = _id;
		this.nome = nome;
	}
	
	public Especialidade(String nome) {
		super();
		this.nome = nome;
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
	
	// ---------------------- DAO -------------------
	private static EspecialidadeMySQL dao = new EspecialidadeMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.find(_id) != null)){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	public static List<Especialidade> all(){
		return dao.all();
	}
	
	public static Especialidade find(int pk){
		return dao.find(pk);
	}
	
	public static Especialidade find(String nome){
		return dao.find(nome);
	}
}
