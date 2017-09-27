package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.HorarioMedico;
import model.Medico;
import suporte.Alertas;

public class CadHorarioMedicoController {

	private Stage dialogStage;
	private HorarioMedico horarioEdit = null;
	
	@FXML
    private ComboBox<String> cbMedico;
	@FXML
    private ComboBox<String> cbDiaSemana;
	@FXML
    private ComboBox<String> cbInicioHorario;
	@FXML
    private ComboBox<String> cbFinalHorario;
	@FXML
    private ComboBox<Integer> cbQtdPacientes;
	@FXML
    private TableView<HorarioMedico> tvHorario;
	@FXML
    private Button btNovo;
	@FXML
    private Button btSalvar;
	@FXML
    private Button btCancelar;
	@FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    
    public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    @FXML
    public void initialize(){
    	ativaCampos(true);
    	btExcluir.setDisable(true);
    	// Preenchendo as opções do combobox de médicos
    	cbMedico.getItems().clear();
		List<Medico> meds = Medico.all();
		if (!meds.isEmpty()){
			for(Medico med : meds){
				cbMedico.getItems().add(med.getNome());
			}
		} else {
			Optional<ButtonType> result = Alertas.confirmacao("Nenhum médico cadastrado!\n",
					"Deseja cadastrar um médico?");
			if (result.get() == ButtonType.OK){
				Main.medico();
				Main.temMedico = false;
			} else {
				Alertas.informacao("Impossível cadastrar um horário sem ter médico cadastrado!");
				Main.temMedico = false;
			}
		}
		
		// preenchendo as opções do combobox de dia da semana
		cbDiaSemana.getItems().clear();
		cbDiaSemana.getItems().addAll("Domingo", "Segunda", "Terça", "Quarta",
				"Quinta", "Sexta", "Sábado");
		cbDiaSemana.setValue("Segunda");
		
		// preenchendo as opções do combobox de inicio e final do horario
		cbInicioHorario.getItems().clear();
		cbInicioHorario.getItems().addAll("06:00", "06:30", "07:00", "07:30",
				"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
				"11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
				"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
				"18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
				"22:00");
		cbInicioHorario.setValue("08:00");
		cbFinalHorario.getItems().clear();
		cbFinalHorario.getItems().addAll("06:00", "06:30", "07:00", "07:30",
				"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
				"11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
				"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
				"18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
				"22:00");
		cbFinalHorario.setValue("08:00");
		
		// preenchendo as opções do combobox de qtd de pacientes
		cbQtdPacientes.getItems().clear();
		cbQtdPacientes.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
				39, 40, 41, 42, 43, 45, 46, 47, 48, 49, 50);
		cbQtdPacientes.setValue(1);
		
		//Preenchimento da tableview
		TableColumn<HorarioMedico, String> colDiaSemana = new TableColumn<HorarioMedico, String>("Dia da Semana");
		colDiaSemana.setCellValueFactory(data -> new SimpleStringProperty( data.getValue().getDiaSemana()) );
		colDiaSemana.setPrefWidth(120);
		
		TableColumn<HorarioMedico, String> colHorario = new TableColumn<HorarioMedico, String>("Horário");
		colHorario.setCellValueFactory(data -> new SimpleStringProperty( data.getValue().getHorario()) );
		colHorario.setPrefWidth(140);
		
		TableColumn<HorarioMedico, String> colQtdPaciente = new TableColumn<HorarioMedico, String>("Qtd. Pacientes");
		colQtdPaciente.setCellValueFactory(data -> new SimpleStringProperty( data.getValue().getQtdPacientes().toString()) );
		colQtdPaciente.setPrefWidth(140);
		
		tvHorario.getColumns().addAll(colDiaSemana, colHorario, colQtdPaciente);	
	}
    
	@FXML
    public void novo(ActionEvent e) {
		ativaCampos(false);
		
		cbDiaSemana.setValue("Segunda");
		cbInicioHorario.setValue("08:00");
		cbFinalHorario.setValue("08:00");
		cbQtdPacientes.setValue(1);
    }

    @FXML
    public void salvar(ActionEvent e) {
    	Medico med = Medico.findByName(cbMedico.getValue());
    	if (isInputValid()){
    		if (horarioEdit == null){ // novo horário
    			HorarioMedico horario = new HorarioMedico(
    					med.getId(),
    					cbDiaSemana.getValue(),
    					cbInicioHorario.getValue()+" às "+cbFinalHorario.getValue(),
    					cbQtdPacientes.getValue());
    			horario.save();
    			
    			Alertas.informacao("Horário salvo com sucesso!");
    		} else {
    			horarioEdit.setIdMedico(med.getId());
    			horarioEdit.setDiaSemana(cbDiaSemana.getValue());
    			horarioEdit.setHorario(cbInicioHorario.getValue()+" às "+cbFinalHorario.getValue());
    			horarioEdit.setQtdPacientes(cbQtdPacientes.getValue());
    			horarioEdit.save();
    			updateList("medico");
    			horarioEdit = null;
    			Alertas.informacao("Dados de horário editados com sucesso!");
    		}
    		ativaCampos(true);
        	updateList("medico");
        	cbDiaSemana.setValue("Segunda");
    		cbInicioHorario.setValue("08:00");
    		cbFinalHorario.setValue("08:00");
    		cbQtdPacientes.setValue(1);
    	}
    }

    @FXML
    public void cancelar(ActionEvent e) {
    	ativaCampos(true);
    	btExcluir.setDisable(true);
    	updateList("limpar");
    	cbMedico.setValue(null);
		cbDiaSemana.setValue("Segunda");
		cbInicioHorario.setValue("08:00");
		cbFinalHorario.setValue("08:00");
		cbQtdPacientes.setValue(1);
    }

    @FXML
    public void editar(ActionEvent e) {
    	ativaCampos(false);
    }

    @FXML
    public void excluir(ActionEvent e) {
    	Optional<ButtonType> result = Alertas.confirmacao(
    			"Excluir horário!",
    			"Temcerteza que deseja excluir este horário?");
    	if (result.get() == ButtonType.OK){
    		horarioEdit.delete();
	    	ativaCampos(true);
	    	btExcluir.setDisable(true);
	    	cbDiaSemana.setValue("Segunda");
			cbInicioHorario.setValue("08:00");
			cbFinalHorario.setValue("08:00");
			cbQtdPacientes.setValue(1);
			updateList("medico");
    	}
    }

    @FXML
    public void fechar(ActionEvent e) {
    	dialogStage.close();
    }
    
    @FXML
	private void  handleClick(MouseEvent e){
    	if (e.getButton() == MouseButton.PRIMARY){
    		horarioEdit = tvHorario.getSelectionModel().getSelectedItem();
    		cbDiaSemana.setValue(horarioEdit.getDiaSemana());
    		cbInicioHorario.setValue(horarioEdit.getHorario().substring(0, 5));
    		cbFinalHorario.setValue(horarioEdit.getHorario().substring(9));
    		cbQtdPacientes.setValue(horarioEdit.getQtdPacientes());
    		btExcluir.setDisable(false);
    	}
	}
    
    @FXML
	private void  handleMedico(){
    	cbMedico.valueProperty().addListener((obs, oldItem, newItem) -> {
             if (newItem != null) {
             	updateList("medico");
          	}
         });
	}
    
    public void ativaCampos(boolean b){
    	btNovo.setDisable(!b);
		btSalvar.setDisable(b);
		btEditar.setDisable(!b);
		cbDiaSemana.setDisable(b);
		cbInicioHorario.setDisable(b);
		cbFinalHorario.setDisable(b);
		cbQtdPacientes.setDisable(b);
    }
    
    private void updateList(String tipo) {
		tvHorario.getItems().clear();
		List<HorarioMedico> horarios = null;
		switch (tipo) {
		case "medico":
			Medico med = Medico.findByName(cbMedico.getValue());
			horarios = HorarioMedico.allById(med.getId());
			break;
		default:
			horarios = HorarioMedico.all();
			break;
		}
		if (tipo.equals("limpar")){
			tvHorario.getItems().clear();
		} else {
			for(HorarioMedico h : horarios){
				tvHorario.getItems().add(h);
			}
		}
	}
    
    private boolean isInputValid() {
    	Date horaInicio = null;
    	Date horaFinal = null;
		String errorMessage = "";
		if (cbMedico.getValue().isEmpty() || cbMedico.getValue() == null){
			errorMessage += "É preciso selecionar um médico!\n";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String horaIni = cbInicioHorario.getValue();
			horaInicio = sdf.parse(horaIni);
			String horaFim = cbFinalHorario.getValue();
			horaFinal = sdf.parse(horaFim);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (horaInicio.equals(horaFinal) || horaInicio.after(horaFinal)){
			errorMessage += "Selecione um horário final maior que o horário inicial!\n";
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
