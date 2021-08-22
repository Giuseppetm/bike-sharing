package gui;

import java.io.IOException;
import java.util.NoSuchElementException;

import dati.AbbonamentoDAOPostgres;
import dominio.Abbonamento;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HomepageGUIController {
    @FXML
    private TextField codiceAbbonamentoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    
    @FXML
    private Button registrazioneButton;
    
    /**
     * Questo metodo permette di verificare i dati inseriti nei campi per il login, 
     * e in caso di autenticazione avvenuta con successo porta l'utente alla schermata principale.
     */
    @FXML
    public void effettuaLogin(ActionEvent event) {
    	AbbonamentoDAOPostgres abbonamentoDao = new AbbonamentoDAOPostgres();
    	Abbonamento abbonamento = null;
    	String codiceAbbonamento = codiceAbbonamentoField.getText();
    	String passwordAbbonamento = passwordField.getText();
    	
    	if (codiceAbbonamento.isBlank() || passwordAbbonamento.isBlank()) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Inserire il codice utente e la password.");
    		a.show();
    		return;
    	}
    	
    	try { abbonamento = abbonamentoDao.effettuaLogin(codiceAbbonamento, passwordAbbonamento); }
    	catch (IllegalStateException e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	} catch (NoSuchElementException e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("SchermataPrincipale.fxml"));
			Parent mainChoiceParent = loader.load();
			Scene scene = new Scene(mainChoiceParent);
			SchermataPrincipaleGUIController schermataPrincipaleController = (SchermataPrincipaleGUIController) loader.getController();
			schermataPrincipaleController.setAbbonamento(abbonamento);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Questo metodo permette di cambiare scena e visualizzare la schermata di registrazione.
     */
    @FXML
    public void goToRegistrazione(ActionEvent event) {
    	try {
			Parent mainChoiceParent = FXMLLoader.load(getClass().getResource("Registrazione.fxml"));
			Scene scene = new Scene(mainChoiceParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    
}