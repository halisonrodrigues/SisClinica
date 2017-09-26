package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Empresa;

public class EmpresaMySQL extends DAO_MySQL{
	int id=0;
	public EmpresaMySQL(){
		open();
		try {
			Statement stm = con.createStatement();
			stm.executeUpdate(
					"Create table if not exists EMPRESA ("
					+ "id Integer AUTO_INCREMENT, "
					+ "nome varchar(255), "
					+ "endereco varchar(255), "
					+ "bairro varchar(60), "
					+ "cidade varchar(60), "
					+ "estado char(2), "
					+ "cep varchar(10), "
					+ "telefone varchar(20), "
					+ "email varchar(255), "
					+ "cnpj varchar(20), "
					+ "insc_estadual varchar(42), "
//					+ "logo_grande blob, "
//					+ "logo_pequeno blob,"
					+ "PRIMARY KEY(id));");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void create(Empresa emp){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "INSERT INTO Empresa VALUES ("
					+ id+", '"
					+ emp.getNomeEmpresa()+"', '"
					+ emp.getEndereco()+"', '"
					+ emp.getBairro()+"', '"
					+ emp.getCidade()+"', '"
					+ emp.getEstado()+"', '"
					+ emp.getCep()+"', '"
					+ emp.getTelefone()+"', '"
					+ emp.getEmail()+"', '"
					+ emp.getCnpj()+"', '"
					+ emp.getInscricao_estadual()+"');";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void update(Empresa emp){
		open();
		try {
			Statement stm = con.createStatement();
			String qr = "UPDATE Empresa SET "
					+ "nome = '"+emp.getNomeEmpresa()+"', "
					+ "endereco = '"+emp.getEndereco()+"', "
					+ "bairro = '"+emp.getBairro()+"', "
					+ "cidade = '"+emp.getCidade()+"', "
					+ "estado = '"+emp.getEstado()+"', "
					+ "cep = '"+emp.getCep()+"', "
					+ "telefone = '"+emp.getTelefone()+"', "
					+ "email = '"+emp.getEmail()+"', "
					+ "cnpj = '"+emp.getCnpj()+"', "
					+ "insc_estadual = '"+emp.getInscricao_estadual()+"' "
					+ "WHERE id = "+emp.get_id()+";";
			stm.executeUpdate(qr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public Empresa find(){
		Empresa result = null;
		open();
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery(
					"SELECT * FROM Empresa;");
			
			if (rs.next()){
				Empresa e = new Empresa(
						rs.getInt(1), //id
						rs.getString(2), //nome
						rs.getString(3), //endereço
						rs.getString(4), //bairro
						rs.getString(5), //cidade
						rs.getString(6), //estado
						rs.getString(7), //cep
						rs.getString(8), //telefone
						rs.getString(9), //email
						rs.getString(10), //cnpj
						rs.getString(11)); //inscrição estadual
				result = e;
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
