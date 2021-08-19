package gui;

import java.io.IOException;

import dominio.Abbonamento;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SchermataPrincipaleGUIController {
	private Abbonamento abbonamento;
	
    @FXML
    private Button noleggiButton;
    
    @FXML
    private Button strumentiAmministrativiButton;
    
    @FXML
    private Button logoutButton;
    
    public void setAbbonamento(Abbonamento abbonamento) {
    	this.abbonamento = abbonamento;
    }
    
    @FXML
    public void effettuaLogout(ActionEvent event) {
    	// Gestire il nullamento del riferimento all'abbonamento e tutto il resto ?? penso non serva
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
    
    @FXML
    public void goToNoleggi(ActionEvent event) {
    	// Da gestire se mandare in finiscinoleggio o effettuanoleggio
    }
    
    @FXML
    public void goToStrumentiAmministrativi(ActionEvent event) {
    	try {
			Parent mainChoiceParent = FXMLLoader.load(getClass().getResource("StrumentiAmministrativi.fxml"));
			Scene scene = new Scene(mainChoiceParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
}