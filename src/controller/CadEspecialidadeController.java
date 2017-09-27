package controller;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Especialidade;
import model.Medico;
import suporte.Alertas;

public class CadEspecialidadeController {
	
	private Stage dialogStage;
	private Especialidade especEdit = null;

	@FXML
	private TextField tfEspecialidade;
	@FXML
	private TableView<Especialidade> tvEspecialidade;
	@FXML
	private Button btNovo;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	@FXML
	private Button btEditar;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void initialize(){
		btSalvar.setDisable(true);
		btCancelar.setDisable(true);
		tfEspecialidade.setDisable(true);
		
		TableColumn<Especialidade, String> colId = new TableColumn<Especialidade, String>("Cod.");
		colId.setCellValueFactory(data -> new SimpleStringProperty(String.format("%04d", data.getValue().get_id())));
		colId.setPrefWidth(45);
		
		TableColumn<Especialidade, String> colNome = new TableColumn<Especialidade, String>("Nome da Especialidade");
		colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
		colNome.setPrefWidth(300);
			
		tvEspecialidade.getColumns().addAll(colId, colNome);
			
		updateList();		
	}
	
	@FXML
	public void novo(){
		btNovo.setDisable(true);
		btSalvar.setDisable(false);
		btCancelar.setDisable(false);
		btEditar.setDisable(true);
		tfEspecialidade.setDisable(false);
		tfEspecialidade.setText("");
		tfEspecialidade.requestFocus();
	}
	
	@FXML
	public void salvar(){
		String mensagemErro = "";
		if (tfEspecialidade.getText().isEmpty()){
			mensagemErro += "É preciso colocar o nome da especialidade!";
		}
		if (mensagemErro.equals("")){
			if (especEdit == null){ // Nova especialidade
				Especialidade espec = new Especialidade(tfEspecialidade.getText());
				espec.save();
				
				Alertas.informacao("Especialidade salva com sucesso!");
			} else { //edição
				especEdit.setNome(tfEspecialidade.getText());
				especEdit.save();
				especEdit = null;
				
				Alertas.informacao("Especialidade editada com sucesso!");
			}
			updateList();
			btNovo.setDisable(false);
			btSalvar.setDisable(true);
			btCancelar.setDisable(true);
			btEditar.setDisable(false);
			tfEspecialidade.setText("");
			tfEspecialidade.setDisable(true);
		} else {
			Alertas.alertErros(mensagemErro);
		}
	}
	
	@FXML
	public void editar(){
		if (especEdit != null){
			
			tfEspecialidade.setDisable(false);
			btNovo.setDisable(true);
			btSalvar.setDisable(false);
			btCancelar.setDisable(false);
			btEditar.setDisable(true);
		} else {
			Alertas.informacao("Nenhuma especialidade selecionada!");
		}
	}
	
	@FXML
	public void cancelar(){
		btNovo.setDisable(false);
		btSalvar.setDisable(true);
		btCancelar.setDisable(true);
		btEditar.setDisable(false);
		tfEspecialidade.setText("");
		tfEspecialidade.setDisable(true);
	}
	
	@FXML
	public void fechar(){
		dialogStage.close();
	}
	
	@FXML
	private void  handleClick(MouseEvent e){
    	if (e.getButton() == MouseButton.PRIMARY){
    		especEdit = tvEspecialidade.getSelectionModel().getSelectedItem();
    		tfEspecialidade.setText(especEdit.getNome());
    	}
	}
	
	private void updateList() {
		tvEspecialidade.getItems().clear();
		List<Especialidade> especs = Especialidade.all();
		for(Especialidade e : especs){
			tvEspecialidade.getItems().add(e);
		}
	}
}
