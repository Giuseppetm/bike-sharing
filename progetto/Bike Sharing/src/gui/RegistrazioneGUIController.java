package gui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;

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
	
	@FXML
	private ChoiceBox<String> meseScadenzaCartaChoiceBox;
	
	@FXML
	private ChoiceBox<String> annoScadenzaCartaChoiceBox;
	
	@FXML
	private TextField cvvCartaTextField;
	
	/**
	 * Questo metodo permette di effettuare la registrazione; nel caso di successo,
	 * l'utente viene riportato alla homepage per poter poi effettuare il login.
	 */
	@FXML
	public void effettuaRegistrazione(ActionEvent event) {
		AbbonamentoDAOPostgres abbonamentoDao = new AbbonamentoDAOPostgres();
		Abbonamento abbonamento = null;
		CartaDiCredito carta = null;
		
		if (tipoAbbonamentoChoiceBox.getValue() == null || passwordField.getText().isBlank()) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Compila tutti i campi riguardanti i dati dell'abbonamento.");
    		a.show();
    		return;
		}
		
		if (numeroCartaTextField.getText().isBlank() || meseScadenzaCartaChoiceBox.getValue() == null || annoScadenzaCartaChoiceBox.getValue() == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Compila tutti i campi riguardanti i dati della carta di credito.");
    		a.show();
    		return;
		}
		
		try {
			carta = new CartaDiCredito(
				numeroCartaTextField.getText(),
				(meseScadenzaCartaChoiceBox.getValue() + "/" + annoScadenzaCartaChoiceBox.getValue()),
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
				passwordField.getText(),
				tipoAbbonamentoChoiceBox.getValue(),
				carta,
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
	    	Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Il nuovo abbonamento è stato creato con successo. Ecco il tuo codice utente, che dovrai ricordare per effettuare il login nel sistema: " + abbonamento.getCodice());
			a.showAndWait();
	    	a = new Alert(AlertType.INFORMATION);
			a.setContentText("Ricorda che hai fino al giorno " + abbonamento.getDataScadenzaValidità() + " per utilizzare l'abbonamento.");
			a.showAndWait();
		} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
		}
		
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
	
	/**
	 * Questo metodo permette di cambiare scena e ritornare alla homepage.
	 */
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
	
	/**
	 * Questo metodo inizializza i vari ChoiceBox che sono necessari per effettuare la registrazione.
	 */
	public void initialize() {
		tipoAbbonamentoChoiceBox.getItems().setAll(TipoAbbonamento.values());
    	for(int i = 1; i <= 12; i++) meseScadenzaCartaChoiceBox.getItems().add(String.format("%02d", i));
    	for(int i = LocalDate.now().getYear(); i < LocalDate.now().getYear() + 10; i++) annoScadenzaCartaChoiceBox.getItems().add(Integer.toString(i));
	}
}