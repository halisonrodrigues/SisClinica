package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Empresa;
import suporte.Alertas;

public class CadEmpresaController {
	
	private Stage dialogStage;
	private Empresa empresaSalva;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfEndereco;
	@FXML
	private TextField tfBairro;
	@FXML
	private TextField tfCidade;
	@FXML
	private ComboBox<String> cbEstado;
	@FXML
	private TextField tfCep;
	@FXML
	private TextField tfTelefone;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfCnpj;
	@FXML
	private TextField tfInscricaoEstadual;
	@FXML
	private Button btSalvarObj;
	@FXML
	private Button btEditarObj;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	protected void initialize(){
		cbEstado.getItems().clear();
		cbEstado.getItems().addAll("AC", "AL", "AP", "AM", "BA",
				"CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
				"RN", "RS", "RO, RR", "SC",	"SP", "SE", "TO");
		cbEstado.setValue("PE");
		
		Empresa emp = Empresa.find();
		if (emp != null){
			tfNome.setText(emp.getNomeEmpresa());
			tfEndereco.setText(emp.getEndereco());
			tfBairro.setText(emp.getBairro());
			tfCidade.setText(emp.getCidade());
			cbEstado.setValue(emp.getEstado());
			tfCep.setText(emp.getCep());
			tfTelefone.setText(emp.getTelefone());
			tfEmail.setText(emp.getEmail());
			tfCnpj.setText(emp.getCnpj());
			tfInscricaoEstadual.setText(emp.getInscricao_estadual());
			ativaCampos(false);
		}
	}
	
	@FXML
	protected void btSalvar(ActionEvent e){
		try {
			if (tfNome.getText().isEmpty()){
				throw new RuntimeException("O campo Nome não pode ser vazio!");
			}
			if (tfEndereco.getText().isEmpty()){
				throw new RuntimeException("O campo Endereço não pode ser vazio!");
			}
			if (tfBairro.getText().isEmpty()){
				throw new RuntimeException("O campo Bairro não pode ser vazio!");
			}
			if (tfCidade.getText().isEmpty()){
				throw new RuntimeException("O campo Cidade não pode ser vazio!");
			}
			if (tfCep.getText().isEmpty()){
				throw new RuntimeException("O campo CEP não pode ser vazio!");
			}
			if (tfTelefone.getText().isEmpty()){
				throw new RuntimeException("O campo Telefone não pode ser vazio!");
			}
			if (tfEmail.getText().isEmpty()){
				throw new RuntimeException("O campo Email não pode ser vazio!");
			}
			if (tfCnpj.getText().isEmpty()){
				throw new RuntimeException("O campo CNPJ não pode ser vazio!");
			}
			if (tfInscricaoEstadual.getText().isEmpty()){
				throw new RuntimeException("O campo Inscrição Estadual não pode ser vazio!");
			}
			
			Empresa emp = Empresa.find();
			
			if (emp != null){
				emp.setNomeEmpresa(tfNome.getText());
				emp.setEndereco(tfEndereco.getText());
				emp.setBairro(tfBairro.getText());
				emp.setCidade(tfCidade.getText());
				emp.setEstado(cbEstado.getValue());
				emp.setCep(tfCep.getText());
				emp.setTelefone(tfTelefone.getText());
				emp.setEmail(tfEmail.getText());
				emp.setCnpj(tfCnpj.getText());
				emp.setInscricao_estadual(tfInscricaoEstadual.getText());
				emp.save();
				Alertas.informacao("Os dados da empresa foram salvos com sucesso!");
			} else {
				Empresa empresa = new Empresa(
						tfNome.getText(),
						tfEndereco.getText(),
						tfBairro.getText(),
						tfCidade.getText(),
						cbEstado.getValue(),
						tfCep.getText(),
						tfTelefone.getText(),
						tfEmail.getText(),
						tfCnpj.getText(),
						tfInscricaoEstadual.getText() );
				empresa.save();
				Alertas.informacao("Os dados da empresa foram salvos com sucesso!");
			}
		} catch (Exception ex) {
		}
	}
	
	@FXML
	protected void btEditar(){
		ativaCampos(true);
	}
	
	@FXML
	protected void cancelar(){
		tfNome.setText("");
		tfEndereco.setText("");
		tfBairro.setText("");
		tfCidade.setText("");
		cbEstado.setValue("");
		tfCep.setText("");
		tfTelefone.setText("");
		tfEmail.setText("");
		tfCnpj.setText("");
		tfInscricaoEstadual.setText("");
	}
	
	@FXML
	protected void btFechar(){
		tfNome.setText("");
		tfEndereco.setText("");
		tfBairro.setText("");
		tfCidade.setText("");
		cbEstado.setValue("");
		tfCep.setText("");
		tfTelefone.setText("");
		tfEmail.setText("");
		tfCnpj.setText("");
		tfInscricaoEstadual.setText("");
		dialogStage.close();
	}
	
	public void ativaCampos(boolean b){
		tfNome.setEditable(b);
		tfEndereco.setEditable(b);
		tfBairro.setEditable(b);
		tfCidade.setEditable(b);
		cbEstado.setDisable(!b);
		tfCep.setEditable(b);
		tfTelefone.setEditable(b);
		tfEmail.setEditable(b);
		tfCnpj.setEditable(b);
		tfInscricaoEstadual.setEditable(b);
		btSalvarObj.setDisable(!b);
		btEditarObj.setDisable(b);
	}
	//----- GETS -----
	public TextField getTfNome() {
		return tfNome;
	}
	public TextField getTfEndereco() {
		return tfEndereco;
	}
	public TextField getTfBairro() {
		return tfBairro;
	}
	public TextField getTfCidade() {
		return tfCidade;
	}
	public ComboBox<String> getCbEstado() {
		return cbEstado;
	}
	public TextField getTfCep() {
		return tfCep;
	}
	public TextField getTfTelefone() {
		return tfTelefone;
	}
	public TextField getTfEmail() {
		return tfEmail;
	}
	public TextField getTfCnpj() {
		return tfCnpj;
	}
	public TextField getTfInscricaoEstadual() {
		return tfInscricaoEstadual;
	}
}
