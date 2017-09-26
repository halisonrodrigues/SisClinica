package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioMySQL extends DAO_MySQL{
	int id = 0;
	
	public UsuarioMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "Create table if not exists USUARIO ("
					+ "id Integer AUTO_INCREMENT, "
					+ "nome varchar(50), "
					+ "cpf varchar(15), "
					+ "usuario varchar(15), "
					+ "senha varchar(130), "
					+ "nivel_acesso varchar(15), "
					+ "status char(1), "
					+ "data_cadastro Date, "
					+ "PRIMARY KEY(id));";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(Usuario user){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO Usuario VALUES ("
					+ id+", '"
					+ user.getNome()+"', '"
					+ user.getCpf()+"', '"
					+ user.getUsuario()+"', '"
					+ user.getSenha()+"', '"
					+ user.getNívelAcesso()+"', '"
					+ user.getStatus()+"', '"
					+ user.getDataCadastro()+"');";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(Usuario user){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE Usuario SET "
					+ "nome = '"+user.getNome()+"', "
					+ "cpf = '"+user.getCpf()+"', "
					+ "usuario = '"+user.getUsuario()+"', "
					+ "senha = '"+user.getSenha()+"', "
					+ "nivel_acesso = '"+user.getNívelAcesso()+"', "
					+ "status = '"+user.getStatus()+"', "
					+ "data_cadastro = '"+user.getDataCadastro()+"' "
					+ "WHERE id = "+user.get_id()+";";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<Usuario> all(){
		ArrayList<Usuario> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM Usuario ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Usuario u = new Usuario(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("usuario"),
						rs.getString("senha"),
						rs.getString("nivel_acesso"),
						rs.getString("status"),
						rs.getDate("data_cadastro"));
				result.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public List<Usuario> all(String nivel){
		ArrayList<Usuario> result = new ArrayList<>();
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM Usuario where nivel_acesso != '"+nivel+"'ORDER BY id;";
			ResultSet rs = stm.executeQuery(qr);
			
			while (rs.next()){
				Usuario u = new Usuario(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("usuario"),
						rs.getString("senha"),
						rs.getString("nivel_acesso"),
						rs.getString("status"),
						rs.getDate("data_cadastro"));
				result.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public Usuario find(int pk){
		Usuario user = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM Usuario WHERE id = "+pk+";";
			ResultSet rs = stm.executeQuery(qr);
			
			if(rs.next()){
				user = new Usuario(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("usuario"),
						rs.getString("senha"),
						rs.getString("nivel_acesso"),
						rs.getString("status"),
						rs.getDate("data_cadastro"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return user;
	}
	
	public Usuario find(String user){
		Usuario result = null;
		
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "SELECT * FROM Usuario WHERE usuario = '"+user+"';";
			ResultSet rs = stm.executeQuery(qr);
			
			if(rs.next()){
				Usuario u = new Usuario(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("usuario"),
						rs.getString("senha"),
						rs.getString("nivel_acesso"),
						rs.getString("status"),
						rs.getDate("data_cadastro"));
				result = u;
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
