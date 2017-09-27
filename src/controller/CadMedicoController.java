package controller;

import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Especialidade;
import model.Medico;
import suporte.Alertas;

public class CadMedicoController {

	private Stage dialogStage;
	private Medico medicoEdit = null;
	boolean busca = false;
	
	@FXML
    private TableView<Medico> tvMedico;
    @FXML
    private ComboBox<String> cbEspecialidade;
    @FXML
    private TextField tfCpf;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btNovo;
    @FXML
    private Button btBuscarNome;
    @FXML
    private Button btBuscarEspecialidade;
    @FXML
    private TextField tfCrm;
    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNome;

    @FXML
    public void initialize(){
    	ativaCampos(true);
		
		cbEspecialidade.getItems().clear();
		List<Especialidade> especs = Especialidade.all();
		for(Especialidade espec : especs){
			cbEspecialidade.getItems().add(espec.getNome());
		}
		
		TableColumn<Medico, String> colId = new TableColumn<Medico, String>("Cod.");
		colId.setCellValueFactory(data -> new SimpleStringProperty( String.format("%04d", data.getValue().getId())) );
		colId.setPrefWidth(45);
		
		TableColumn<Medico, String> colNome = new TableColumn<Medico, String>("Nome");
		colNome.setCellValueFactory(data -> new SimpleStringProperty( data.getValue().getNome()) );
		colNome.setPrefWidth(317);
		
		tvMedico.getColumns().addAll(colId, colNome);
			
		updateList("");
	}

    
    public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
    void novo(ActionEvent e) {
		medicoEdit = null;
		busca = false;
		ativaCampos(false);
		
		tfNome.setText("");
		cbEspecialidade.setValue("");
		tfCrm.setText("");
		tfCpf.setText("");
		tfNome.requestFocus();
    }

    @FXML
    void salvar(ActionEvent e) {
    	Especialidade espec = Especialidade.find(cbEspecialidade.getValue());
    	if (isInputValid()){
    		if (medicoEdit == null){ //novo médico
    			Date data1 = new Date();
				java.sql.Date data2 = new java.sql.Date(data1.getTime());
    			Medico med = new Medico(
    					tfNome.getText(),
    					espec.get_id(),
    					tfCrm.getText(),
    					tfCpf.getText(),
    					data2);
    			med.save();
    			
    			Alertas.informacao("Médico salvo com sucesso!");
    		} else {
    			medicoEdit.setNome(tfNome.getText());
    			medicoEdit.setIdEspecialidade(espec.get_id());
    			medicoEdit.setCrm(tfCrm.getText());
    			medicoEdit.setCpf(tfCpf.getText());
    			medicoEdit.save();
    			
    			medicoEdit = null;
    			Alertas.informacao("Dados editados com sucesso!");
    		}
    		updateList("");
			ativaCampos(true);
    		tfNome.setText("");
			cbEspecialidade.setValue("");
			tfCrm.setText("");
			tfCpf.setText("");
    	}
    }

    @FXML
    void cancelar(ActionEvent e) {
    	busca = false;
    	medicoEdit = null;
    	ativaCampos(true);
    	
    	tfNome.setText("");
		cbEspecialidade.setValue("");
		tfCrm.setText("");
		tfCpf.setText("");
		updateList("");
    }

    @FXML
    void editar(ActionEvent e) {
    	busca = false;
    	if(medicoEdit != null){
    		ativaCampos(false);
    		tfNome.requestFocus();
    	} else {
    		Alertas.informacao("Nenhum médico foi selecionado!");
    	}
    }

    @FXML
    void fechar(ActionEvent e) {
    	dialogStage.close();
    }
    
    @FXML
	private void  handleClick(MouseEvent e){
    	if (e.getButton() == MouseButton.PRIMARY){
    		medicoEdit = tvMedico.getSelectionModel().getSelectedItem();
    		tfNome.setText(medicoEdit.getNome());
    		cbEspecialidade.setValue(Medico.findNomeEspecialidade(medicoEdit.getIdEspecialidade()));
    		tfCrm.setText(medicoEdit.getCrm());
    		tfCpf.setText(medicoEdit.getCpf());
    		cbEspecialidade.setDisable(true);
    		tfNome.setDisable(true);
    	}
	}
    
    @FXML
    public void buscaNome(){
    	busca = true;
    	tfNome.setDisable(false);
    	tfNome.setText("");
    	btNovo.setDisable(true);
    	btBuscarEspecialidade.setDisable(true);
    	updateList("");
    }
    
    @FXML
    public void buscaEspecialidade(){
    	busca = true;
    	cbEspecialidade.setDisable(false);
    	btNovo.setDisable(true);
    	btBuscarNome.setDisable(true);
    }
    
    @FXML
	private void  handleNome(javafx.scene.input.KeyEvent tecla){
		// Criar pesquisa com o texto do campo
    	if (busca){
    		updateList("nome");
    	}
	}
    
    @FXML
	private void  handleEspecialidade(){
    	cbEspecialidade.valueProperty().addListener((obs, oldItem, newItem) -> {
             if (newItem != null) {
            	 if (busca){
            		cbEspecialidade.setDisable(true);
             		updateList("especialidade");
          		} 
             }
         });
    	
		
		
	}
    
    private void updateList(String tipo) {
		tvMedico.getItems().clear();
		List<Medico> meds = null;
		switch (tipo){
		case "nome":
			meds = Medico.allNome(tfNome.getText());
			break;
		case "especialidade":
			meds = Medico.findByEspecialidade(cbEspecialidade.getValue());
			break;
		default: meds = Medico.all();
		}
		for(Medico med : meds){
			tvMedico.getItems().add(med);
		}
	}
    
    public void ativaCampos(boolean b){
    	btBuscarNome.setDisable(!b);
    	btBuscarEspecialidade.setDisable(!b);
	    btNovo.setDisable(!b);
		btSalvar.setDisable(b);
		btEditar.setDisable(!b);
		tfNome.setDisable(b);
		tfCrm.setDisable(b);
		tfCpf.setDisable(b);
		cbEspecialidade.setDisable(b);
    }
    
    private boolean isInputValid() {
		String errorMessage = "";
		if (tfNome.getText().isEmpty()){
			errorMessage += "O nome do médico precisa ser preenchido!\n";
		}
		if (cbEspecialidade.getValue() == null){
			errorMessage += "É preciso escolher uma especialidade!\n";
		}
		if (tfCrm.getText().isEmpty()){
			errorMessage += "O CRM do médico precisa ser preenchido!\n";
		}
		if (tfCpf.getText().isEmpty()){
			errorMessage += "O CPF do médico precisa ser preenchido!\n";
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
