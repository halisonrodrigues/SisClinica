package model;

import java.util.List;

import DAO.ConvenioMySQL;

public class Convenio {

	private Integer _id;
	private String nome;
	private float valor;
	private String recibo;
	
	public Convenio(Integer _id, String nome, float valor, String recibo) {
		super();
		this._id = _id;
		this.nome = nome;
		this.valor = valor;
		this.recibo = recibo;
	}

	public Convenio(String nome, float valor, String recibo) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.recibo = recibo;
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

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getRecibo(){
		return recibo;
	}
	
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	// ------------------- DAO ---------------------
	private static ConvenioMySQL dao = new ConvenioMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.find(_id) != null) ){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	
	public static List<Convenio> all(){
		return dao.all();
	}
	
	public static Convenio find(int pk){
		return dao.find(pk);
	}
}
