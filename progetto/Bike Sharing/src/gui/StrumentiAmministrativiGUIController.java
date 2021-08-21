package gui;

import java.io.IOException;

import dati.BiciclettaDAOPostgres;
import dati.DatiStatisticiDAOPostgres;
import dati.MorsaDAOPostgres;
import dati.TotemDAOPostgres;
import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.TipoMorsa;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class StrumentiAmministrativiGUIController {
	private Abbonamento abbonamento;
	
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}
	
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    @FXML
    private Button creaTotemButton; 
    
    @FXML
    private Button eliminaTotemButton;
    
    @FXML
    private Button creaMorsaButton;
    
    @FXML
    private Button eliminaMorsaButton;
    
    @FXML
    private Button inserisciBiciclettaButton;
    
    @FXML
    private Button eliminaBiciclettaButton; 
    
    @FXML
    private Button riparaBiciclettaButton;
    
    @FXML
    private TextField indirizzoTotemTextField;
    
    @FXML
    private ComboBox<Totem> indirizzoEliminazioneTotemComboBox;
    
    @FXML
    private ComboBox<Totem> indirizzoTotemCreaMorsaComboBox;
    
    @FXML
    private ComboBox<Totem> indirizzoTotemEliminaMorsaComboBox; 
    
    @FXML
    private ComboBox<Totem> indirizzoTotemInserisciBiciciclettaComboBox;
    
    @FXML
    private ComboBox<Totem> indirizzoTotemEliminaBiciclettaComboBox;
    
    @FXML
    private ComboBox<TipoMorsa> tipoMorsaCreaMorsaComboBox;
    
    @FXML
    private ComboBox<TipoMorsa> tipoMorsaEliminaMorsaComboBox;
    
    @FXML
    private ComboBox<TipoBicicletta> tipoBiciclettaInserisciBiciclettaComboBox;
    
    @FXML
    private ComboBox<TipoBicicletta> tipoBiciclettaEliminaBiciclettaComboBox;
    
    @FXML
    private ComboBox<Bicicletta> riparaBiciclettaComboBox;
    
    @FXML
    private Label abbonamentiCreati;
    
    @FXML
    private Label abbonamentiAttivi;
    
    @FXML
    private Label abbonamentiSospesi; 
    
    @FXML
    private Label noleggiEffettuati; 
    
    @FXML
    private Label bicicletteDanneggiate; 
    
    @FXML
    private Label totemPiùUtilizzato; 
    
    @FXML
    private Label numeroTotem;
    
    @FXML
    public void creaPostazioneTotem(ActionEvent event) {
    	String indirizzoTotem = indirizzoTotemTextField.getText();
    	
    	if (indirizzoTotem.isBlank()) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Inserisci l'indirizzo della postazione con totem che vuoi creare.");
    		a.show();
    		return;
    	}
    	
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	try {
    	totemDao.aggiungiTotem(new Totem(indirizzoTotem));
    	} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Nuova postazione con totem (indirizzo " + indirizzoTotem + ") creata con successo.");
		a.showAndWait();
    	indirizzoTotemTextField.setText(""); /* Riporto il valore a quello di default (vuoto) */
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void eliminaPostazioneTotem(ActionEvent event) {
    	Totem totem = indirizzoEliminazioneTotemComboBox.getValue();
    	
    	if (totem == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona la postazione con totem che vuoi eliminare.");
    		a.show();
    		return;
    	}
    	
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	try {
    		totemDao.rimuoviTotem(totem);
    	} catch (Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Postazione con totem (indirizzo " + totem.getIndirizzo() + ") eliminata con successo.");
		a.showAndWait();
		indirizzoEliminazioneTotemComboBox.setValue(null); /* Riporto il valore a quello di default (vuoto) */
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void creaMorsaPostazione(ActionEvent event) {
    	Totem totem = indirizzoTotemCreaMorsaComboBox.getValue();
    	TipoMorsa tipoMorsa = tipoMorsaCreaMorsaComboBox.getValue();
    	
    	if (totem == null || tipoMorsa == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona l'indirizzo del totem e il tipo di morsa da creare.");
    		a.show();
    		return;
    	}
    	
    	MorsaDAOPostgres morsaDao = new MorsaDAOPostgres();
    	try {
    		morsaDao.aggiungiMorsa(totem, tipoMorsa);
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Morsa di tipo " + tipoMorsa + " creata con successo nella postazione con totem (indirizzo " + totem.getIndirizzo() + ").");
		a.showAndWait();
		indirizzoTotemCreaMorsaComboBox.setValue(null);
		tipoMorsaCreaMorsaComboBox.setValue(null);
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void eliminaMorsaPostazione(ActionEvent event) {
    	Totem totem = indirizzoTotemEliminaMorsaComboBox.getValue();
    	TipoMorsa tipoMorsa = tipoMorsaEliminaMorsaComboBox.getValue();
    	
    	if (totem == null || tipoMorsa == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona l'indirizzo del totem e il tipo di morsa da eliminare.");
    		a.show();
    		return;
    	}
    	
    	MorsaDAOPostgres morsaDao = new MorsaDAOPostgres();
    	try {
    		morsaDao.rimuoviMorsa(totem, tipoMorsa);
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Morsa di tipo " + tipoMorsa + " eliminata con successo dalla postazione con totem (indirizzo " + totem.getIndirizzo() + ").");
		a.showAndWait();
		indirizzoTotemEliminaMorsaComboBox.setValue(null);
		tipoMorsaEliminaMorsaComboBox.setValue(null);
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void inserisciBiciclettaPostazione(ActionEvent event) {
    	Totem totem = indirizzoTotemInserisciBiciciclettaComboBox.getValue();
    	TipoBicicletta tipoBicicletta = tipoBiciclettaInserisciBiciclettaComboBox.getValue();
    	
    	if (totem == null || tipoBicicletta == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona l'indirizzo del totem e il tipo di bicicletta da inserire.");
    		a.show();
    		return;
    	}
    	
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	try {
    		biciclettaDao.aggiungiBicicletta(totem, tipoBicicletta);
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Bicicletta di tipo " + tipoBicicletta + " aggiunta con successo nella postazione con totem (indirizzo " + totem.getIndirizzo() + ").");
		a.showAndWait();
		indirizzoTotemInserisciBiciciclettaComboBox.setValue(null);
		tipoBiciclettaInserisciBiciclettaComboBox.setValue(null);
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void eliminaBiciclettaPostazione(ActionEvent event) {
    	Totem totem = indirizzoTotemEliminaBiciclettaComboBox.getValue();
    	TipoBicicletta tipoBicicletta = tipoBiciclettaEliminaBiciclettaComboBox.getValue();
    	
    	if (totem == null || tipoBicicletta == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona l'indirizzo del totem e il tipo di bicicletta da eliminare.");
    		a.show();
    		return;
    	}
    	
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	try {
    		biciclettaDao.rimuoviBicicletta(totem, tipoBicicletta);
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Bicicletta di tipo " + tipoBicicletta + " rimossa con successo dalla postazione con totem (indirizzo " + totem.getIndirizzo() + ").");
		a.showAndWait();
		indirizzoTotemEliminaBiciclettaComboBox.setValue(null);
		tipoBiciclettaEliminaBiciclettaComboBox.setValue(null);
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void riparaBiciclettaDanneggiata(ActionEvent event) {
    	Bicicletta bicicletta = riparaBiciclettaComboBox.getValue();
    	
    	if (bicicletta == null) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText("Seleziona la bicicletta che vuoi riparare.");
    		a.show();
    		return;
    	}
    	
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	try {
    		biciclettaDao.riparaBicicletta(bicicletta);
    	} catch(Exception e) {
    		Alert a = new Alert(AlertType.ERROR);
    		a.setContentText(e.getMessage());
    		a.show();
    		return;
    	}
    	
    	Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Bicicletta con ID " + bicicletta.getId() + " riparata con successo!");
		a.showAndWait();
		riparaBiciclettaComboBox.setValue(null);
    	this.initialize(); /* Per aggiornare la lista dei contenuti dei ChoiceBox */
    }
    
    @FXML
    public void goToSchermataPrincipale(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("SchermataPrincipale.fxml"));
			Parent mainChoiceParent = loader.load();
			Scene scene = new Scene(mainChoiceParent);
			SchermataPrincipaleGUIController schermataPrincipaleController = (SchermataPrincipaleGUIController) loader.getController();
			schermataPrincipaleController.setAbbonamento(this.abbonamento);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    
    public void initialize() {
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	DatiStatisticiDAOPostgres datiStatisticiDao = new DatiStatisticiDAOPostgres();
    	
    	try { 
    		indirizzoEliminazioneTotemComboBox.getItems().setAll(totemDao.getListaTotem()); 
        	indirizzoTotemCreaMorsaComboBox.getItems().setAll(totemDao.getListaTotem());
        	indirizzoTotemEliminaMorsaComboBox.getItems().setAll(totemDao.getListaTotem());
        	indirizzoTotemInserisciBiciciclettaComboBox.getItems().setAll(totemDao.getListaTotem());
        	indirizzoTotemEliminaBiciclettaComboBox.getItems().setAll(totemDao.getListaTotem());
    	} catch (Exception e) {
        	Alert a = new Alert(AlertType.INFORMATION);
    		a.setContentText("Attenzione: non ci sono ancora postazioni con totem registrate nel sistema.");
    		a.showAndWait();
    	}

    	tipoBiciclettaInserisciBiciclettaComboBox.getItems().setAll(TipoBicicletta.values());
    	tipoBiciclettaEliminaBiciclettaComboBox.getItems().setAll(TipoBicicletta.values());
    	tipoMorsaCreaMorsaComboBox.getItems().setAll(TipoMorsa.values());
    	tipoMorsaEliminaMorsaComboBox.getItems().setAll(TipoMorsa.values());
    	
    	try { riparaBiciclettaComboBox.getItems().setAll(biciclettaDao.getBicicletteDanneggiate()); } catch (Exception e) {}
    	
    	
    	abbonamentiCreati.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamenti()));
    	abbonamentiAttivi.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamentiAttivi()));
    	abbonamentiSospesi.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamentiSospesi()));
    	noleggiEffettuati.setText(Integer.toString(datiStatisticiDao.getNumeroNoleggiEffettuati()));
    	bicicletteDanneggiate.setText(Integer.toString(datiStatisticiDao.getNumeroBicicletteDanneggiate()));
    	try { totemPiùUtilizzato.setText(datiStatisticiDao.getTotemPiùUtilizzato()); } catch (Exception e) {}
    	numeroTotem.setText(Integer.toString(datiStatisticiDao.getNumeroTotem()));
    }
}