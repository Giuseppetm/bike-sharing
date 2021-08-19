package gui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import dati.AbbonamentoDAOPostgres;
import dominio.Abbonamento;
import dominio.CartaDiCredito;
import dominio.TipoAbbonamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegistrazioneGUIController {
	@FXML
	private Button registrazioneButton;
	
	@FXML
	private Button backToHomepageButton;
	
	@FXML
	private ChoiceBox<TipoAbbonamento> tipoAbbonamentoChoiceBox;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private CheckBox studenteCheckBox;
	
	@FXML
	private TextField numeroCartaTextField;
	
	@FXML // to-do: adjust this
	private ChoiceBox<String> meseScadenzaCartaChoiceBox;
	
	@FXML // to-do: adjust this
	private ChoiceBox<String> annoScadenzaCartaChoiceBox;
	
	@FXML
	private TextField cvvCartaTextField;
	
	@FXML
	public void effettuaRegistrazione(ActionEvent event) {
		AbbonamentoDAOPostgres abbonamentoDao = new AbbonamentoDAOPostgres();
		Abbonamento abbonamento = null;
		CartaDiCredito carta = null;
		
		// To-do: verifica se ci vuole il == null o il isBlank() nei choiceBox
		if (numeroCartaTextField.getText().isBlank() || meseScadenzaCartaChoiceBox.getValue() == null || annoScadenzaCartaChoiceBox.getValue() == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Compila tutti i campi riguardanti i dati della carta di credito.");
    		a.show();
    		return;
		}
		
		if ( tipoAbbonamentoChoiceBox.getValue() == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Compila tutti i campi riguardanti i dati della carta di credito.");
    		a.show();
    		return;
		}
		
		try {
			carta = new CartaDiCredito(
				numeroCartaTextField.getText(),
				(meseScadenzaCartaChoiceBox + "/" + annoScadenzaCartaChoiceBox),
				cvvCartaTextField.getText()
			);
		} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
		}
		
		try {
			abbonamento = new Abbonamento(
				...
				studenteCheckBox.isSelected()
			);
		} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
		}
		
		try {
			abbonamentoDao.registraAbbonamento(abbonamento);
		} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
		}
	}
	
	@FXML
	public void backToHomepage(ActionEvent event) {
    	try {
			Parent mainChoiceParent = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
			Scene scene = new Scene(mainChoiceParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}