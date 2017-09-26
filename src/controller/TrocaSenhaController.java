package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import suporte.Alertas;
import suporte.Seguranca;

public class TrocaSenhaController {

	private Stage dialogStage;

	@FXML
	private TextField tfUsuario;
	@FXML
	private PasswordField pwSenha;
	@FXML
	private PasswordField pwNovaSenha;
	@FXML
	private PasswordField pwRepitaNovaSenha;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void initialize(){
		tfUsuario.setText(Main.usuarioLogado.getUsuario());
		tfUsuario.setDisable(true);
		pwSenha.requestFocus();
	}
	
	@FXML
	public void Salvar(){
	String senha = Seguranca.criptografia(pwSenha.getText());
	if (senha.equals(Main.usuarioLogado.getSenha())){
		if (pwNovaSenha.getText().equals(pwRepitaNovaSenha.getText())){
			Main.usuarioLogado.setSenha(Seguranca.criptografia(pwRepitaNovaSenha.getText()));
			Main.usuarioLogado.save();
			dialogStage.close();
			Alertas.informacao("Senha alterada com sucesso!");
		} else {
			Alertas.informacao("A confirmação da nova senha está incorreta!");
			pwRepitaNovaSenha.requestFocus();
		}
	} else {
		Alertas.informacao("A senha informada está incorreta!");
		pwSenha.requestFocus();
	}
		
		
	}
	
	@FXML
	public void Cancelar(){
		dialogStage.close();
	}
}
