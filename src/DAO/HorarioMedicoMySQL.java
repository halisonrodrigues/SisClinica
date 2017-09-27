package DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.HorarioMedico;

public class HorarioMedicoMySQL extends DAO_MySQL{
	int id = 0;
	
	public HorarioMedicoMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "CREATE TABLE IF NOT EXISTS horario_medico ("
					+ "id integer AUTO_INCREMENT, "
					+ "id_medico integer, "
					+ "horario varchar(15), "
					+ "qtd_pacientes integer, "
					+ "dia_semana varchar(7), "
					+ "PRIMARY KEY (id));";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(HorarioMedico horario){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO horario_medico VALUES ("
					+ id + ", ' "
					+ horario.getIdMedico() + "', '"
					+ horario.getHorario() + "', '"
					+ horario.getQtdPacientes() + "', '"
					+ horario.getDiaSemana() + "');";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(HorarioMedico horario){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE horario_medico SET "
					+ "id_medico = '" + horario.getIdMedico() + "', "
					+ "horario = '" + horario.getHorario() + "', "
					+ "qtd_pacientes = '" + horario.getQtdPacientes() + "', "
					+ "dia_semana = '" + horario.getDiaSemana() + "', "
					+ "WHERE id = " + horario.get_id() + ";";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<HorarioMedico> all(){
		ArrayList<HorarioMedico> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM horario_medico ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				HorarioMedico horario = new HorarioMedico(
						rs.getInt("id"),
						rs.getInt("id_medico"),
						rs.getString("horario"),
						rs.getInt("qtd_pacientes"),
						rs.getString("dia_semana"));
				result.add(horario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public HorarioMedico findById(int pk){
		HorarioMedico horario = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM horario_medico WHERE id = "+pk+";";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				horario = new HorarioMedico(
						rs.getInt("id"),
						rs.getInt("id_medico"),
						rs.getString("horario"),
						rs.getInt("qtd_pacientes"),
						rs.getString("dia_semana"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return horario;
	}
}
