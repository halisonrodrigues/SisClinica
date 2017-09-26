package controller;
	
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Convenio;
import model.Empresa;
import model.Usuario;
import suporte.Seguranca;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	protected static Usuario usuarioLogado; 
	private static Stage stage;
	private static BorderPane mainLayout;
	private static BorderPane logoInicio;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stage = primaryStage;
		stage.setTitle("SisCLINICA - Controle de Clínica Médica");
		
		List<Usuario> users = Usuario.all();
		if (users.isEmpty()){
			Date data1 = new Date();
			java.sql.Date data2 = new java.sql.Date(data1.getTime());
			Usuario adm1 = new Usuario("Gamaliel Tavares", "00000000000", "gama", Seguranca.criptografia("leilamag"), "Administrador", "A", data2);
			adm1.save();
			Usuario adm2 = new Usuario("Halison Rodrigues", "04794801416", "halison", Seguranca.criptografia("19015233"), "Administrador", "A", data2);
			adm2.save();
		}
		List<Convenio> conv = Convenio.all();
		if (conv.isEmpty()){
			Convenio c = new Convenio(0, "Particular", 100, "Sim");
			c.save();
		}
		login();
		initMainLayout();		
	}
	
	public void initMainLayout(){
		// CARREGAMENTO DO LAYOUT PRINCIPAL
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/MainLayout.fxml"));
			mainLayout = (BorderPane) loader.load();
			mainLayout.setCenter(logoInicio);
			
			Scene scene = new Scene(mainLayout);
			stage.setScene(scene);
			stage.show();
			stage.setMaximized(true);
			if (Empresa.find() == null){
				empresa();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void login(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/AcessoLayout.fxml"));
			BorderPane login = (BorderPane) loader.load();

			Stage loginStage = new Stage();
			loginStage.initOwner(stage);
			Scene scene = new Scene(login);
			loginStage.setScene(scene);
			loginStage.initStyle(StageStyle.UNDECORATED);
			AcessoController controller = loader.getController();
			controller.setDialogStage(loginStage);
			controller.getTfUsuario().requestFocus();
			loginStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void empresa(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CadEmpresaLayout.fxml"));
			BorderPane empresa = (BorderPane) loader.load();

			Stage empresaStage = new Stage();
			empresaStage.initOwner(stage);
			Scene scene = new Scene(empresa);
			empresaStage.setScene(scene);
			CadEmpresaController controller = loader.getController();
			controller.setDialogStage(empresaStage);
			empresaStage.setResizable(false);
			empresaStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void usuario(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CadUsuarioLayout.fxml"));
			BorderPane usuario = (BorderPane) loader.load();

			Stage usuarioStage = new Stage();
			usuarioStage.initOwner(stage);
			Scene scene = new Scene(usuario);
			usuarioStage.setScene(scene);
			CadUsuarioController controller = loader.getController();
			controller.setDialogStage(usuarioStage);
			usuarioStage.setResizable(false);
			usuarioStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void trocaSenha(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/TrocaSenhaLayout.fxml"));
			BorderPane trocaSenha = (BorderPane) loader.load();

			Stage trocaSenhaStage = new Stage();
			trocaSenhaStage.initOwner(stage);
			Scene scene = new Scene(trocaSenha);
			trocaSenhaStage.setScene(scene);
			TrocaSenhaController controller = loader.getController();
			controller.setDialogStage(trocaSenhaStage);
			trocaSenhaStage.setResizable(false);
			trocaSenhaStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void especialidade(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CadEspecialidadeLayout.fxml"));
			BorderPane especialidade = (BorderPane) loader.load();

			Stage usuarioStage = new Stage();
			usuarioStage.initOwner(stage);
			Scene scene = new Scene(especialidade);
			usuarioStage.setScene(scene);
			CadEspecialidadeController controller = loader.getController();
			controller.setDialogStage(usuarioStage);
			usuarioStage.show();
			usuarioStage.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void convenio(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CadConvenioLayout.fxml"));
			BorderPane convenio = (BorderPane) loader.load();

			Stage usuarioStage = new Stage();
			usuarioStage.initOwner(stage);
			Scene scene = new Scene(convenio);
			usuarioStage.setScene(scene);
			CadConvenioController controller = loader.getController();
			controller.setDialogStage(usuarioStage);
			usuarioStage.show();
			usuarioStage.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void medico(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CadMedicoLayout.fxml"));
			BorderPane medico = (BorderPane) loader.load();

			Stage medicoStage = new Stage();
			medicoStage.initOwner(stage);
			Scene scene = new Scene(medico);
			medicoStage.setScene(scene);
			CadMedicoController controller = loader.getController();
			controller.setDialogStage(medicoStage);
			medicoStage.setResizable(false);
			medicoStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
