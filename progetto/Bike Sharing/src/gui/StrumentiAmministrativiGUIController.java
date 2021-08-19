package gui;

import java.io.IOException;

import dati.BiciclettaDAOPostgres;
import dati.DatiStatisticiDAOPostgres;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StrumentiAmministrativiGUIController {
	private Abbonamento abbonamento;
	
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}
	
    @FXML
    private Button backToSchermataPrincipaleButton;
    
    @FXML
    private Button creaTotemButton, eliminaTotemButton, creaMorsaButton, eliminaMorsaButton, inserisciBiciclettaButton, eliminaBiciclettaButton, riparaBiciclettaButton;
    
    @FXML
    private TextField indirizzoTotemTextField;
    
    @FXML
    private ComboBox<Totem> indirizzoEliminazioneTotemComboBox, indirizzoTotemCreaMorsaComboBox, indirizzoTotemEliminaMorsaComboBox, indirizzoTotemInserisciBiciciclettaComboBox, indirizzoTotemEliminaBiciclettaComboBox;
    
    @FXML
    private ComboBox<TipoMorsa> tipoMorsaCreaMorsaComboBox, tipoMorsaEliminaMorsaComboBox;
    
    @FXML
    private ComboBox<TipoBicicletta> tipoBiciclettaInserisciBiciclettaComboBox, tipoBiciclettaEliminaBiciclettaComboBox;
    
    @FXML
    private ComboBox<Bicicletta> riparaBiciclettaComboBox;
    
    @FXML
    private Label abbonamentiCreati, abbonamentiAttivi, abbonamentiSospesi, noleggiEffettuati, bicicletteDanneggiate, totemPiùUtilizzato, numeroTotem;
    
    @FXML
    public void creaPostazioneTotem(ActionEvent event) {
    	
    }
    
    @FXML
    public void eliminaPostazioneTotem(ActionEvent event) {
    	
    }
    
    @FXML
    public void creaMorsaPostazione(ActionEvent event) {
    	
    }
    
    @FXML
    public void eliminaMorsaPostazione(ActionEvent event) {
    	
    }
    
    @FXML
    public void inserisciBiciclettaPostazione(ActionEvent event) {
    	
    }
    
    @FXML
    public void eliminaBiciclettaPostazione() {
    	
    }
    
    @FXML
    public void riparaBiciclettaDanneggiata() {
    	
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
    
    void initialized() {
    	TotemDAOPostgres totemDao = new TotemDAOPostgres();
    	BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
    	DatiStatisticiDAOPostgres datiStatisticiDao = new DatiStatisticiDAOPostgres();
    	indirizzoEliminazioneTotemComboBox.getItems().setAll(totemDao.getListaTotem());
    	indirizzoTotemCreaMorsaComboBox.getItems().setAll(totemDao.getListaTotem());
    	indirizzoTotemEliminaMorsaComboBox.getItems().setAll(totemDao.getListaTotem());
    	indirizzoTotemInserisciBiciciclettaComboBox.getItems().setAll(totemDao.getListaTotem());
    	indirizzoTotemEliminaBiciclettaComboBox.getItems().setAll(totemDao.getListaTotem());
    	tipoBiciclettaInserisciBiciclettaComboBox.getItems().setAll(TipoBicicletta.values());
    	tipoBiciclettaEliminaBiciclettaComboBox.getItems().setAll(TipoBicicletta.values());
    	tipoMorsaCreaMorsaComboBox.getItems().setAll(TipoMorsa.values());
    	tipoMorsaEliminaMorsaComboBox.getItems().setAll(TipoMorsa.values());
    	riparaBiciclettaComboBox.getItems().setAll(biciclettaDao.getBicicletteDanneggiate());
    	
    	abbonamentiCreati.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamenti()));
    	abbonamentiAttivi.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamentiAttivi()));
    	abbonamentiSospesi.setText(Integer.toString(datiStatisticiDao.getNumeroAbbonamentiSospesi()));
    	noleggiEffettuati.setText(Integer.toString(datiStatisticiDao.getNumeroNoleggiEffettuati()));
    	bicicletteDanneggiate.setText(Integer.toString(datiStatisticiDao.getNumeroBicicletteDanneggiate()));
    	totemPiùUtilizzato.setText(datiStatisticiDao.getTotemPiùUtilizzato());
    	numeroTotem.setText(Integer.toString(datiStatisticiDao.getNumeroTotem()));
    }
}