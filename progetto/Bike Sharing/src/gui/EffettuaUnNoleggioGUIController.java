package gui;

import java.io.IOException;

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

public class EffettuaUnNoleggioGUIController {
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    @FXML
    private Button effettuaNoleggioButton;
    
    @FXML
    public void effettuaNoleggio(ActionEvent event) {
    	// To-do: ricordati di rimandare nella schermata principale poi
    }
    
    @FXML
    public void goToSchermataPrincipale(ActionEvent event) {
    	try {
			Parent mainChoiceParent = FXMLLoader.load(getClass().getResource("SchermataPrincipale.fxml"));
			Scene scene = new Scene(mainChoiceParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
}