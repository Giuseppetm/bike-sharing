package gui;

import java.io.IOException;

import dati.NoleggioDAOPostgres;
import dominio.Abbonamento;
import dominio.Noleggio;
import dominio.Totem;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;

public class NoleggioInCorsoGUIController {
	private Abbonamento abbonamento;
	
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}
	
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    @FXML
    private ChoiceBox<Totem> postazioneTotemChoiceBox;
    
    @FXML
    private CheckBox segnalaDanniBiciclettaCheckBox;
    
    @FXML
    private Button terminaNoleggioButton;
    
    @FXML
    public void terminaNoleggio(ActionEvent event) {
    	NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
    	Noleggio noleggioInCorso = noleggioDao.getNoleggioInCorso(this.abbonamento);
    	
    	try {
    		noleggioDao.finisciNoleggio(this.abbonamento, null);	
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	long durataNoleggio = noleggioInCorso.getDurataNoleggio();
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Il noleggio è terminato correttamente! Durata in minuti: " + durataNoleggio + "; Prezzo complessivo: " + noleggioInCorso.getBicicletta().calcolaCosto((int)durataNoleggio, abbonamento.isStudente()));
		a.showAndWait();
    	
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
    
    @FXML
    public void goToSchermataPrincipale(ActionEvent event) {
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
}