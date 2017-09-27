package DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Medico;

public class MedicoMySQL extends DAO_MySQL{
	int id = 0;
	public MedicoMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "CREATE TABLE IF NOT EXISTS medico ("
					+ "id integer AUTO_INCREMENT,"
					+ "nome varchar(50), "
					+ "id_especialidade integer, "
					+ "crm varchar(20), "
					+ "cpf varchar(15), "
					+ "data_cadastro Date, "
					+ "PRIMARY KEY (id));";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(Medico med){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO medico VALUES ("
					+ id + ", '"
					+ med.getNome() +"', "
					+ med.getIdEspecialidade() + ", '"
					+ med.getCrm() + "', '"
					+ med.getCpf() + "', '"
					+ med.getDataCadastro() + "');";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(Medico med){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE medico SET "
					+ "nome = '" + med.getNome() + "', "
					+ "id_especialidade = " + med.getIdEspecialidade() + ", "
					+ "crm = '" + med.getCrm() + "', "
					+ "cpf = '" + med.getCpf() + "', "
					+ "data_cadastro = '" + med.getDataCadastro() +"' "
					+ "WHERE id = " + med.getId() + ";";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<Medico> all(){
		ArrayList<Medico> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM medico ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while(rs.next()){
				Medico med = new Medico(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getInt("id_especialidade"),
						rs.getString("crm"),
						rs.getString("cpf"),
						rs.getDate("data_cadastro"));
				result.add(med);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public Medico findById(int pk){
		Medico med = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM medico WHERE id = "+pk+";";
			ResultSet rs = stm.executeQuery(qr);
			
			if (rs.next()){
				med = new Medico(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getInt("id_especialidade"),
						rs.getString("crm"),
						rs.getString("cpf"),
						rs.getDate("data_cadastro"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return med;
	}
	
	public List<Medico> allByNome(String nome){
		ArrayList<Medico> meds = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM medico WHERE nome LIKE '"+nome+"%';";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Medico med = new Medico(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getInt("id_especialidade"),
						rs.getString("crm"),
						rs.getString("cpf"),
						rs.getDate("data_cadastro"));
				meds.add(med);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return meds;
	}
	
	public List<Medico> allByEspecialidade(String nomeEspec){
		ArrayList<Medico> meds = new ArrayList<>();
		// select * from medico where id = (select id from especialidade where nome = 'Cardiologia');
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM medico WHERE id_especialidade = ("
					+ "SELECT id FROM especialidade WHERE nome = '"+nomeEspec+"');";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Medico med = new Medico(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getInt("id_especialidade"),
						rs.getString("crm"),
						rs.getString("cpf"),
						rs.getDate("data_cadastro"));
				meds.add(med);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return meds;
	}
	
	public String nomeEspecialidade(int pkEspec){
		String nomeEspec = "";
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM especialidade WHERE id = "+pkEspec+";";
			ResultSet rs = stm.executeQuery(qr);
			
			if (rs.next()){
				nomeEspec = rs.getString("nome");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return nomeEspec;
	}

	public Medico findByName(String nome) {
		Medico med = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM medico WHERE nome = '"+nome+"';";
			ResultSet rs = stm.executeQuery(qr);
			
			if (rs.next()){
				med = new Medico(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getInt("id_especialidade"),
						rs.getString("crm"),
						rs.getString("cpf"),
						rs.getDate("data_cadastro"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return med;
	}
}
