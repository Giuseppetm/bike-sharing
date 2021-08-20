package gui;

import java.io.IOException;

import dati.BiciclettaDAOPostgres;
import dati.NoleggioDAOPostgres;
import dati.TotemDAOPostgres;
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
    	/* In questo modo viene evitato che venga segnalato nuovamente che la bicicletta è danneggiata */
		NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
		if (noleggioDao.getNoleggioInCorso(abbonamento).getBicicletta().isDanneggiata()) {
			segnalaDanniBiciclettaCheckBox.setSelected(true);
			segnalaDanniBiciclettaCheckBox.setDisable(true);
		}
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
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	Noleggio noleggio = null;
    	
    	if (postazioneTotemChoiceBox.getValue() == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona il totem da cui prelevare la bicicletta e il tipo di bicicletta.");
    		a.show();
    		return;
    	}
    	
    	try {
    		noleggio = noleggioDao.finisciNoleggio(this.abbonamento, postazioneTotemChoiceBox.getValue());
    		if (segnalaDanniBiciclettaCheckBox.isSelected()) biciclettaDao.comunicaDanni(noleggio.getBicicletta());
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Il noleggio è terminato correttamente! Durata in minuti: " + noleggio.getDurataNoleggio() + "; Prezzo complessivo: " + noleggio.getBicicletta().calcolaCosto((int) noleggio.getDurataNoleggio(), abbonamento.isStudente()));
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
    
    public void initialize() {
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	postazioneTotemChoiceBox.getItems().setAll(totemDao.getListaTotem());
    }
}