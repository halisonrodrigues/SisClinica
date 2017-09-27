package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import suporte.DataHora;

public class MainController{
	
	@FXML
	private Label lbData;
	@FXML
	private Label lbHora;
	@FXML
	private Label lbUsuarioLogado;
	
	@FXML
	public void initialize(){
		lbUsuarioLogado.setText(Main.usuarioLogado.getNome());
	}
	@FXML
	public void fechar(){
		System.exit(0);
	}
	
	@FXML
	public void cadastroEmpresa(ActionEvent e){
		Main.empresa();
	}
	
	@FXML
	public void cadastroUsuario(ActionEvent e){
		Main.usuario();
	}
	
	@FXML
	public void trocaSenha(ActionEvent e){
		Main.trocaSenha();
	}
	
	@FXML
	public void especialidade(ActionEvent e){
		Main.especialidade();
	}
	
	@FXML
	public void convenio(ActionEvent e){
		Main.convenio();
	}
	
	@FXML
	public void medico(ActionEvent e){
		Main.medico();
	}
	
	@FXML
	public void horario(ActionEvent e){
		Main.horario();
	}
	
	public void mostrarHora() {
        DataHora ah = new DataHora(lbHora);
        ah.mostrarData(true);
        Thread thHora = ah;
        thHora.start();
    }
}
