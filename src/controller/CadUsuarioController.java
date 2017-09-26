package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Medico;
import model.Usuario;
import suporte.Alertas;
import suporte.Seguranca;

public class CadUsuarioController {

	private Stage dialogStage;
	private Usuario usuarioEdit = null;
	
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfCpf;
	@FXML
	private RadioButton rbAtivo;
	@FXML
	private RadioButton rbInativo;
	@FXML
	private TextField tfUsuario;
	@FXML
	private ComboBox<String> cbNivelAcesso;
	@FXML
	private PasswordField pwSenha;
	@FXML
	private PasswordField pwRepitaSenha;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btEditar;
	@FXML
	private Button btNovo;
	@FXML
	private Button btCancelar;
	@FXML
	private TableView<Usuario> tvUsuario;
	String senha = null;
	String tipoSalvamento;
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	@FXML
	protected void initialize(){
		
		btSalvar.setDisable(true);
		btCancelar.setDisable(true);
		ativaCampos(false);
		cbNivelAcesso.getItems().clear();
		if (Main.usuarioLogado.getNívelAcesso().equals("Administrador")){
			cbNivelAcesso.getItems().addAll("Administrador", "Médico", "Assistente", "Secretária");
		} else {
			cbNivelAcesso.getItems().addAll("Médico", "Assistente", "Secretária");
		}

		TableColumn<Usuario, String> colId = new TableColumn<Usuario, String>("Cod.");
		colId.setCellValueFactory(data -> new SimpleStringProperty( String.format("%04d", data.getValue().get_id())) );
		colId.setPrefWidth(45);
			
		TableColumn<Usuario, String> colNome = new TableColumn<Usuario, String>("Nome");
		colNome.setCellValueFactory(data -> new SimpleStringProperty( data.getValue().getNome()) );
		colNome.setPrefWidth(273);
			
		TableColumn<Usuario, String> colDataCadastor = new TableColumn<Usuario, String>("Cadastro");
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		colDataCadastor.setCellValueFactory(data -> new SimpleStringProperty(formataData.format(data.getValue().getDataCadastro())));
			
		tvUsuario.getColumns().addAll(colId, colNome, colDataCadastor);
			
		updateList();
	}
		
	@FXML
	public void novo(ActionEvent e){
		tipoSalvamento = "novo";
		tfNome.setText("");
		tfCpf.setText("");
		rbAtivo.setSelected(true);
		tfUsuario.setText("");
		pwSenha.setText("");
		pwRepitaSenha.setText("");
		ativaCampos(true);
		btNovo.setDisable(true);
		btSalvar.setDisable(false);
		btCancelar.setDisable(false);
		btEditar.setDisable(true);
		tfNome.requestFocus();
	}
	
	@FXML
	public void salvar(ActionEvent e){
		if (isInputValid(tipoSalvamento)){
			String status = "";
			if (rbAtivo.isSelected()){
				status = "A";
			} else {
				status = "I";
			}
			if (usuarioEdit==null){ // novo cadastro
				Date data1 = new Date();
				java.sql.Date data2 = new java.sql.Date(data1.getTime());
				Usuario user = new Usuario(
						tfNome.getText(),
						tfCpf.getText(),
						tfUsuario.getText(),
						senha,
						cbNivelAcesso.getValue(),
						status,
						data2);
				user.save();
				limpaCampos();
				ativaCampos(false);
				updateList();
				btNovo.setDisable(false);
				btSalvar.setDisable(true);
				btCancelar.setDisable(true);
				btEditar.setDisable(false);
				Alertas.informacao("Usuário salvo com sucesso!");
			} else { // edição
				Usuario user = new Usuario(
						usuarioEdit.get_id(),
						tfNome.getText(),
						tfCpf.getText(),
						tfUsuario.getText(),
						usuarioEdit.getSenha(),
						cbNivelAcesso.getValue(),
						status,
						usuarioEdit.getDataCadastro());
				user.save();
				limpaCampos();
				ativaCampos(false);
				updateList();
				btNovo.setDisable(false);
				btSalvar.setDisable(true);
				btCancelar.setDisable(true);
				btEditar.setDisable(false);
				usuarioEdit = null;
				Alertas.informacao("Dados do usuário editados com sucesso!");
			}
		}
	}
	
	@FXML
	public void editar(ActionEvent e){
		tipoSalvamento = "edicao";
		if(usuarioEdit != null){
			
			ativaCampos(true);
			pwSenha.setDisable(true);
			pwRepitaSenha.setDisable(true);
			btSalvar.setDisable(false);
			btCancelar.setDisable(false);
			btNovo.setDisable(true);
			btEditar.setDisable(true);
		} else {
			Alertas.informacao("Nenhum usuário selecionado!");
		}
	}
	
	@FXML
	public void cancelar(ActionEvent e){
		limpaCampos();
		ativaCampos(false);
		btNovo.setDisable(false);
		btSalvar.setDisable(true);
		btCancelar.setDisable(true);
		btEditar.setDisable(false);
	}
	
	@FXML
	public void fechar(ActionEvent e){
		dialogStage.close();
	}
	
	@FXML
	private void  handleClick(MouseEvent e){
    	if (e.getButton() == MouseButton.PRIMARY){
    		usuarioEdit = tvUsuario.getSelectionModel().getSelectedItem();
    		tfNome.setText(usuarioEdit.getNome());
			tfCpf.setText(usuarioEdit.getCpf());
			if (usuarioEdit.getStatus().equals("A")){
				rbAtivo.setSelected(true);
			} else if (usuarioEdit.getStatus().equals("I")){
				rbInativo.setSelected(true);
			}
			cbNivelAcesso.setValue(usuarioEdit.getNívelAcesso());
			tfUsuario.setText(usuarioEdit.getUsuario());
    	}
	}
	
	private void updateList() {
		tvUsuario.getItems().clear();
		List<Usuario> users = null;
		if (Main.usuarioLogado.getNívelAcesso().equals("Administrador")){
			users = Usuario.all();
		} else {
			users = Usuario.allNivel("Administrador");
		}
		for(Usuario u : users){
			tvUsuario.getItems().add(u);
		}
	}
	
	public void ativaCampos(boolean b){
		tfNome.setDisable(!b);
		tfCpf.setDisable(!b);
		tfUsuario.setDisable(!b);
		pwSenha.setDisable(!b);
		pwRepitaSenha.setDisable(!b);
		rbAtivo.setDisable(!b);;
		rbInativo.setDisable(!b);
		cbNivelAcesso.setDisable(!b);
	}
	
	public void limpaCampos(){
		tfNome.setText("");
		tfCpf.setText("");
		tfUsuario.setText("");
		pwSenha.setText("");
		pwRepitaSenha.setText("");
		rbAtivo.setSelected(true);
		rbInativo.setSelected(false);
		cbNivelAcesso.setValue("");
	}
	
	
	private boolean isInputValid(String tipo) {
		String errorMessage = "";
		if (tfNome.getText().isEmpty()){
			errorMessage += "O nome do usuário precisa ser preenchido!\n";
		}
		if (tfCpf.getText().isEmpty()){
			errorMessage += "O CPF do usuário precisa ser preenchido!\n";
		}
		if (cbNivelAcesso.getValue() == null){
			errorMessage += "Seleciona um nível de acesso!\n";
		}
		if (tfUsuario.getText().isEmpty()){
			errorMessage += "O usuario para acesso precisa ser preenchido!\n";
		}
		if (tipo.equals("novo")){
			if (pwSenha.getText().isEmpty()){
				errorMessage += "A senha do usuário precisa ser sefinida!\n";
			}
			if (pwSenha.getText().equals(pwRepitaSenha.getText())){
				senha = Seguranca.criptografia(pwSenha.getText());
			} else {
				errorMessage += "A senha de confirmação está incorreta!\n";
				pwRepitaSenha.requestFocus();
			}
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Mostra a mensagem de erro.
			Alertas.alertErros(errorMessage);
			return false;
		}
	}
}
