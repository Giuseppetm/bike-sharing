package gui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrazioneGUIController {
	@FXML
	private Button registrazioneButton;
	
	@FXML
	private Button backToHomepageButton;
	
	
	public void effettuaRegistrazione(ActionEvent event) {
		
	}
	
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