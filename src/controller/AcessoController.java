package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Usuario;
import suporte.Alertas;
import suporte.Seguranca;

public class AcessoController {
	
	private Stage dialogStage;
	
	@FXML
    private PasswordField pwSenha;
    @FXML
    private TextField tfUsuario;
    
    public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
    
	@FXML
	private void entrar(){
		Main.usuarioLogado = Usuario.findUsuario(tfUsuario.getText());
			if(Main.usuarioLogado != null){
				if (Main.usuarioLogado.getStatus().equals("A")){
					String senhaEntrada = Seguranca.criptografia(pwSenha.getText());
					if(senhaEntrada.equals(Main.usuarioLogado.getSenha())){
						dialogStage.close();
					} else {
						Alertas.informacao("Senha incorreta!");
						pwSenha.setText("");
						pwSenha.requestFocus();
					}
				} else {
					Alertas.informacao("Usuário inativo!");
					tfUsuario.setText("");
					pwSenha.setText("");
					tfUsuario.requestFocus();
				}
			} else {
				Alertas.informacao("O usuário informado não existe!");
				tfUsuario.setText("");
				pwSenha.setText("");
				tfUsuario.requestFocus();
			}
		
	}
	
	@FXML
	public void handleEnter(javafx.scene.input.KeyEvent tecla) throws IOException{
		if (tecla.getCode().equals(KeyCode.ENTER)){
			entrar();
		}
	}
	
	@FXML
	public void cancelar(){
		System.exit(0);
	}

	public PasswordField getPwSenha() {
		return pwSenha;
	}

	public TextField getTfUsuario() {
		return tfUsuario;
	}
	
}
