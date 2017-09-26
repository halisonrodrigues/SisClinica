package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Especialidade;

public class EspecialidadeMySQL extends DAO_MySQL{
	int id = 0;
	
	public EspecialidadeMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "Create table if not exists ESPECIALIDADE ("
					+ "id Integer AUTO_INCREMENT, "
					+ "nome varchar(50), "
					+ "PRIMARY KEY(id));";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(Especialidade espec){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO Especialidade VALUES ("
					+ id+", '"
					+ espec.getNome()+"'); ";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(Especialidade espec){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE Especialidade SET "
					+ "nome = '"+espec.getNome()+"' WHERE id = '"+espec.get_id()+"';";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<Especialidade> all(){
		ArrayList<Especialidade> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM especialidade ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Especialidade espec = new Especialidade(
						rs.getInt("id"),
						rs.getString("nome"));
				result.add(espec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public Especialidade find(int pk){
		Especialidade result = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM especialidade WHERE id = "+pk+";";
			ResultSet rs = stm.executeQuery(qr);
			
			if(rs.next()){
				Especialidade espec = new Especialidade(
						rs.getInt("id"),
						rs.getString("nome"));
				result = espec;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public Especialidade find(String nome){
		Especialidade result = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM especialidade WHERE nome = '"+nome+"';";
			ResultSet rs = stm.executeQuery(qr);
			
			if(rs.next()){
				Especialidade espec = new Especialidade(
						rs.getInt("id"),
						rs.getString("nome"));
				result = espec;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
}
