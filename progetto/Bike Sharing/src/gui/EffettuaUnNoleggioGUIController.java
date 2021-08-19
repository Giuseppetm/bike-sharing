package gui;

import java.io.IOException;

import dati.BiciclettaDAOPostgres;
import dati.NoleggioDAOPostgres;
import dominio.Abbonamento;
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
    
    @FXML
    public void effettuaNoleggio(ActionEvent event) {
    	NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	
    	try {
    		noleggioDao.iniziaNoleggio(this.abbonamento, null, null);
    	} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Noleggio noleggioInCorso = noleggioDao.getNoleggioInCorso(this.abbonamento);
    	int posizioneBicicletta = biciclettaDao.getPosizioneInRastrelliera(noleggioInCorso.getBicicletta());
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Il noleggio è stato creato con successo! La bicicletta sbloccata è presente nella morsa numero: " + posizioneBicicletta + "; Ora di inizio noleggio: " + noleggioInCorso.getOrarioInizioNoleggio());
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