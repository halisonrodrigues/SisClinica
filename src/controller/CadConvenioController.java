package controller;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Convenio;
import model.Medico;
import suporte.Alertas;

public class CadConvenioController {

	private Stage dialogStage;
	private Convenio convEdit = null;
	
	@FXML
    private TextField tfConvenio;
    @FXML
    private TextField tfValor;
    @FXML
    private RadioButton rbSim;
    @FXML
    private RadioButton rbNao;
    @FXML
    private TableView<Convenio> tvConvenio;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btNovo;
    @FXML
    private Button btCancelar;

    public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    @FXML
    public void initialize(){
    	rbSim.setSelected(true);
    	ativaCampos(true);
		
		TableColumn<Convenio, String> colId = new TableColumn<Convenio, String>("Cod.");
		colId.setCellValueFactory(data -> new SimpleStringProperty(String.format("%04d", data.getValue().get_id())));
		colId.setPrefWidth(45);
			
		TableColumn<Convenio, String> colNome = new TableColumn<Convenio, String>("Convênio");
		colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
		colNome.setPrefWidth(195);
			
		TableColumn<Convenio, String> colValor = new TableColumn<Convenio, String>("Valor");
		colValor.setCellValueFactory(data -> new SimpleStringProperty(String.format("R$ %.2f", data.getValue().getValor())));
			
		TableColumn<Convenio, String> colRecibo = new TableColumn<Convenio, String>("Recibo");
		colRecibo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecibo()));
			
		tvConvenio.getColumns().addAll(colId,colNome,colValor,colRecibo);
			
		updateList();
    }

	@FXML
    void novo(ActionEvent e) {
		ativaCampos(false);
		
		rbSim.setSelected(true);
		tfConvenio.setText("");
		tfValor.setText("");
		tfConvenio.requestFocus();
    }

    @FXML
    void salvar(ActionEvent e) {
    	if (isInputValid()){
    		String valor  = tfValor.getText().replace(",", ".");
    		String recibo = "Não";
    		if (rbSim.isSelected()){
    			recibo = "Sim";
    		}
    		if (convEdit == null){
	    		Convenio conv = new Convenio(
	    				tfConvenio.getText(),
	    				Float.parseFloat(valor),
	    				recibo);
	    		conv.save();
	    		updateList();
	    		ativaCampos(true);
	    		
	    		rbSim.setSelected(true);
	    		tfConvenio.setText("");
	    		tfValor.setText("");
    		} else {
    			convEdit.setNome(tfConvenio.getText());
    			convEdit.setValor(Float.parseFloat(valor));
    			convEdit.setRecibo(recibo);
    			convEdit.save();
    			updateList();
    			ativaCampos(true);

    			rbSim.setSelected(true);
    			tfConvenio.setText("");
    			tfValor.setText("");
    		}
    	}
    }

    @FXML
    void cancelar(ActionEvent e) {
    	ativaCampos(true);

		rbSim.setSelected(true);
		tfConvenio.setText("");
		tfValor.setText("");
    }

    @FXML
    void editar(ActionEvent e) {
    	if (convEdit != null){
	    	ativaCampos(false);
			tfConvenio.requestFocus();
    	} else {
    		Alertas.informacao("Nenhum convênio foi selecionado!");
    	}
    }

    @FXML
    void fechar(ActionEvent e) {
    	dialogStage.close();
    }
    
    @FXML
	private void  handleClick(MouseEvent e){
    	if (e.getButton() == MouseButton.PRIMARY){
    		convEdit = tvConvenio.getSelectionModel().getSelectedItem();
    		tfConvenio.setText(convEdit.getNome());
			tfValor.setText(String.format("%.2f", convEdit.getValor()));
			if (convEdit.getRecibo().equals("Sim")){
				rbSim.setSelected(true);
			} else if (convEdit.getRecibo().equals("Não")){
				rbNao.setSelected(true);
			}
    	}
	}
    
    private void updateList() {
		tvConvenio.getItems().clear();
		List<Convenio> convs = Convenio.all();
		for (Convenio c :convs){
			tvConvenio.getItems().add(c);
		}
	}
    
    public void ativaCampos(boolean b){
	    btNovo.setDisable(!b);
		btSalvar.setDisable(b);
		btCancelar.setDisable(b);
		btEditar.setDisable(!b);
		tfConvenio.setDisable(b);
		tfValor.setDisable(b);
		rbSim.setDisable(b);
		rbNao.setDisable(b);
    }
	
    private boolean isInputValid() {
		String errorMessage = "";
		if (tfConvenio.getText().isEmpty()){
			errorMessage += "O nome do convênio precisa ser preenchido!\n";
		}
		if (tfValor.getText().isEmpty()){
			errorMessage += "O valor do convênio precisa ser preenchido!\n";
		}
		try {
			String valor = tfValor.getText();
			Float.parseFloat(valor.replace(",", "."));
		} catch (Exception e) {
			errorMessage += "Insira um valor correto!";
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
