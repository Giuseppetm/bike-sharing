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

public class StrumentiAmministrativiGUIController {
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    /* Tutti i bottoni di gestione
    @FXML
    private Button ;
    */
    
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