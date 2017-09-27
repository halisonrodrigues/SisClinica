package suporte;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Alertas {

	public static void alertErros(String errorMessage){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Erro no preenchimento dos campos!");
		PrintWriter pw;
		try {
			pw = new PrintWriter(errorMessage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String exceptionText = errorMessage;

		Label label = new Label("Os campos inválidos são:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}
	
	public static void informacao(String texto){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Informação");
		alert.setHeaderText(null);
		alert.setContentText(texto);
		alert.showAndWait();
	}
	
	public static Optional<ButtonType> confirmacao(String header, String texto){
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Confirmação!!!");
		confirm.setHeaderText(header);
		confirm.setContentText(texto);
		Optional<ButtonType> result = confirm.showAndWait();
		return result;
	}
}
