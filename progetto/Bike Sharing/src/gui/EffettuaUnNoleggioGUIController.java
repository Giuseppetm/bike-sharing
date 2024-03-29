package gui;

import java.io.IOException;

import dati.BiciclettaDAOPostgres;
import dati.NoleggioDAOPostgres;
import dati.TotemDAOPostgres;
import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.Noleggio;
import dominio.TipoBicicletta;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;

public class EffettuaUnNoleggioGUIController {
	private Abbonamento abbonamento;
	
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}
	
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    @FXML
    private Button effettuaNoleggioButton;
    
    @FXML
    private ChoiceBox<TipoBicicletta> tipoBiciclettaChoiceBox;
    
    @FXML
    private ChoiceBox<Totem> postazioneTotemChoiceBox;
    
    /**
     * Questo metodo permette di effettuare un noleggio; in caso di successo, 
     * l'utente viene riportato alla schermata principale.
     */
    @FXML
    public void effettuaNoleggio(ActionEvent event) {
    	NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	
    	if (postazioneTotemChoiceBox.getValue() == null || tipoBiciclettaChoiceBox.getValue() == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona il totem da cui prelevare la bicicletta e il tipo di bicicletta.");
    		a.show();
    		return;
    	}
    	
    	int posizioneBicicletta = 0;
    	try {
	    	Bicicletta bicicletta = postazioneTotemChoiceBox.getValue().getBicicletta(tipoBiciclettaChoiceBox.getValue());
	    	posizioneBicicletta = biciclettaDao.getPosizioneNellaPostazione(postazioneTotemChoiceBox.getValue(), bicicletta);
    	} catch (Exception e) { /* No need to handle this specific exception */ }
    	
    	try {
    		noleggioDao.iniziaNoleggio(this.abbonamento, postazioneTotemChoiceBox.getValue(), tipoBiciclettaChoiceBox.getValue());
    	} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Noleggio noleggioInCorso = noleggioDao.getNoleggioInCorso(this.abbonamento);
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Il noleggio � stato creato con successo! La bicicletta sbloccata � presente nella morsa numero: " + posizioneBicicletta + "; Ora di inizio noleggio: " + noleggioInCorso.getOrarioInizioNoleggio());
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
    
    /**
     * Questo metodo permette di cambiare scena e tornare alla schermata principale.
     */
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
    
    /**
     * Questo metodo inizializza i vari ChoiceBox con i valori necessari.
     */
    public void initialize() {
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	tipoBiciclettaChoiceBox.getItems().setAll(TipoBicicletta.values());
    	try { postazioneTotemChoiceBox.getItems().setAll(totemDao.getListaTotem()); } catch (Exception e) {
        	Alert a = new Alert(AlertType.INFORMATION);
    		a.setContentText("Attenzione: non ci sono ancora postazioni con totem registrate nel sistema.");
    		a.showAndWait();
    	}
    }
}