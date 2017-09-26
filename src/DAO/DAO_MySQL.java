package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DAO_MySQL {

	protected Connection con;
	public static String status = "Não conectou...";

	public Connection open() {

		String serverName = "localhost";
		String usuario = "root";
		String senha = "";
		final String NOMEDB = "sisclinica";
		String driveName = "com.mysql.jdbc.Driver";
		
		try {
			String url = "jdbc:mysql://"+serverName+"/"+NOMEDB+"?autoReconnect=true&useSSL=false";
			Class.forName(driveName);
			con = DriverManager.getConnection(url, usuario, senha);

			return con;
		} catch (Exception e) {
			System.out.println(e);
			try {
				Class.forName(driveName);
				Connection c = DriverManager.getConnection("jdbc:mysql://"+serverName+"/", usuario,
						senha);
				Statement statement = c.createStatement();
				statement.executeUpdate("CREATE DATABASE "+NOMEDB);
				return open();
			} catch (Exception e2) {
				e2.printStackTrace();
				return null;
			}
		}
	}

	public void close(){
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void createDatabase(Connection con) {
//		try {
//			Statement stm = con.createStatement();
//			stm.executeUpdate(
//					"Create table if not exists EMPRESA ("
//					+ "id Integer AUTO_INCREMENT, "
//					+ "nome varchar(255), "
//					+ "endereco varchar(255), "
//					+ "bairro varchar(60), "
//					+ "cidade varchar(60), "
//					+ "estado char(2), "
//					+ "cep varchar(10), "
//					+ "telefone varchar(20), "
//					+ "email varchar(255), "
//					+ "cnpj varchar(20), "
//					+ "insc_estadual varchar(42), "
//					+ "logo_grande blob, "
//					+ "logo_pequeno blob,"
//					+ "PRIMARY KEY(id));");
//			
//			stm.executeUpdate(
//					"Create table if not exists COMANDA (cod_nota serial PRIMARY KEY NOT NULL, "
//					+ "cod_comanda varchar(4) not null, "
//					+ "data_abertura timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
//					+ "data_fechamento timestamp, "
//					+ "status varchar (10) not null, "
//					+ "motivo_cancelamento varchar(255));");
//			
//			stm.executeUpdate(
//					"Create table if not exists ITEMCOMANDA (id_item serial PRIMARY KEY NOT NULL, "
//					+ "cod_nota int not null, "
//					+ "cod_barras varchar(255) not null, "
//					+ "valor_item decimal not null);");
//			
//			stm.executeUpdate(
//					"Create table if not exists PRODUTO (cod_barras varchar(255) not null, "
//					+ "descricao varchar(255) not null, "
//					+ "valor decimal not null);");
//			
//			stm.executeUpdate(
//					"Create table if not exists USUARIO (id_usuario SERIAL PRIMARY KEY NOT NULL, "
//					+ "nome varchar(16) not null, "
//					+ "senha varchar(255) not null, "
//					+ "permissao varchar(16), "
//					+ "status char not null);");
//	
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
