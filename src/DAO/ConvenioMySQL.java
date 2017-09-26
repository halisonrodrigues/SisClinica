package DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Convenio;

public class ConvenioMySQL extends DAO_MySQL{
	int id = 0;
	
	public ConvenioMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "CREATE TABLE IF NOT EXISTS convenio ("
					+ "id INTEGER AUTO_INCREMENT, "
					+ "nome varchar(50), "
					+ "valor float, "
					+ "recibo varchar(3), "
					+ "PRIMARY KEY(id));";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(Convenio conv){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO convenio VALUES ("
					+ id +", '"
					+ conv.getNome()+ "', '"
					+ conv.getValor()+ "', '"
					+ conv.getRecibo()+ "');";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(Convenio conv){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE convenio SET "
					+ "nome = '" + conv.getNome() + "', "
					+ "valor = '" + conv.getValor() + "', "
					+ "recibo = '" + conv.getRecibo() +"' "
					+ "WHERE id = '"+conv.get_id()+"';";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<Convenio> all(){
		ArrayList<Convenio> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM convenio ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Convenio conv = new Convenio(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getFloat("valor"),
						rs.getString("recibo"));
				result.add(conv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public Convenio find(int pk){
		Convenio conv = null;
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM convenio WHERE id = "+pk+";";
			ResultSet rs = stm.executeQuery(qr);
			
			if (rs.next()){
				conv = new Convenio(
					rs.getInt("id"),
					rs.getString("nome"),
					rs.getFloat("valor"),
					rs.getString("recibo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return conv;
	}
}
