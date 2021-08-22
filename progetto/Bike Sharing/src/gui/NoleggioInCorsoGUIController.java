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
    	/* In questo modo viene evitato che venga segnalato nuovamente che la bicicletta � danneggiata */
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
    
    /**
     * Questo metodo permette di terminare un noleggio; nel caso l'utente raggiunga
     * 3 ammonizioni sar� riportato alla homepage e non potr� rieffettuare il login dato che 
     * l'abbonamento risulter� sospeso. In caso contrario, sar� semplicemente riportato
     * alla schermata principale.
     */
    @FXML
    public void terminaNoleggio(ActionEvent event) {
    	NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	int numeroAmmonizioniPreTermine = this.abbonamento.getAmmonizioni();
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
		a.setContentText("Il noleggio � terminato correttamente! Durata in minuti: " + noleggio.getDurataNoleggio() + "; Prezzo complessivo: " + noleggio.getBicicletta().calcolaCosto((int) noleggio.getDurataNoleggio(), abbonamento.isStudente()));
		a.showAndWait();
		
		if (this.abbonamento.isSospeso()) {
	    	a = new Alert(AlertType.INFORMATION);
			a.setContentText("Attenzione! Hai ricevuto 3 ammonizioni: l'abbonamento � stato sospeso.");
			a.showAndWait();
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
		
		int numeroAmmonizioniPostTermine = this.abbonamento.getAmmonizioni();
		if (numeroAmmonizioniPreTermine != numeroAmmonizioniPostTermine) {
	    	a = new Alert(AlertType.INFORMATION);
			a.setContentText("Attenzione! Hai ricevuto un'ammonizione. Ora hai " + this.abbonamento.getAmmonizioni() + " ammonizioni associate al tuo abbonamento.");
			a.showAndWait();
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
     * Questo metodo inizializza i vari ChoiceBox necessari per effettuare la terminazione del noleggio.
     */
    public void initialize() {
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	try { postazioneTotemChoiceBox.getItems().setAll(totemDao.getListaTotem()); } catch (Exception e) {}
    }
}