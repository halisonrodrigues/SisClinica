package model;

import java.util.List;

import DAO.HorarioMedicoMySQL;

public class HorarioMedico {

	private Integer _id;
	private Integer idMedico;
	private String horario;
	private Integer qtdPacientes;
	private String diaSemana;
	
	public HorarioMedico(Integer _id, Integer idMedico, String horario, Integer qtdPacientes, String diaSemana) {
		super();
		this._id = _id;
		this.idMedico = idMedico;
		this.horario = horario;
		this.qtdPacientes = qtdPacientes;
		this.diaSemana = diaSemana;
	}

	public HorarioMedico(Integer idMedico, String horario, Integer qtdPacientes, String diaSemana) {
		super();
		this.idMedico = idMedico;
		this.horario = horario;
		this.qtdPacientes = qtdPacientes;
		this.diaSemana = diaSemana;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public Integer getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Integer idMedico) {
		this.idMedico = idMedico;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Integer getQtdPacientes() {
		return qtdPacientes;
	}

	public void setQtdPacientes(Integer qtdPacientes) {
		this.qtdPacientes = qtdPacientes;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
		
	// --------------------------- DAO --------------------------
	private static HorarioMedicoMySQL dao = new HorarioMedicoMySQL();
	
	public void save(){
		if ( (_id != null) && (dao.findById(_id) != null)){
			dao.update(this);
		} else {
			dao.create(this);
		}
	}
	
	public static List<HorarioMedico> all(){
		return dao.all();
	}
	
	public static HorarioMedico findById(int pk){
		return dao.findById(pk);
	}
}
