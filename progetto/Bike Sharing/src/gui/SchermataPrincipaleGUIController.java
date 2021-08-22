package gui;

import java.io.IOException;

import dati.NoleggioDAOPostgres;
import dominio.Abbonamento;
import dominio.TipoAbbonamento;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SchermataPrincipaleGUIController {
	private Abbonamento abbonamento;
	
    public void setAbbonamento(Abbonamento abbonamento) {
    	this.abbonamento = abbonamento;
    	/* Nel caso l'utente non sia del tipo personale di servizio non potrà cliccare sul pulsante apposito per gli strumenti amministrativi. */
    	if (this.abbonamento.getTipo() != TipoAbbonamento.PERSONALE_SERVIZIO) strumentiAmministrativiButton.setDisable(true);
    }
	
    @FXML
    private Button noleggiButton;
    
    @FXML
    private Button strumentiAmministrativiButton;
    
    @FXML
    private Button logoutButton;

    /**
     * Questo metodo permette di effettuare il logout e riportare quindi
     * l'utente alla schermata della homepage.
     */
    @FXML
    public void effettuaLogout(ActionEvent event) {
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
    
    /**
     * Questo metodo, a seconda se l'utente ha o meno un noleggio in corso, porta rispettivamente
     * alla schermata per concludere il noleggio, oppure a quella per effettuarne uno nuovo.
     */
    @FXML
    public void goToNoleggi(ActionEvent event) {
    	NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
    	
    	if (noleggioDao.hasNoleggioInCorso(this.abbonamento)) {
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("NoleggioInCorso.fxml"));
    			Parent mainChoiceParent = loader.load();
    			Scene scene = new Scene(mainChoiceParent);
    			NoleggioInCorsoGUIController noleggioInCorsoController = (NoleggioInCorsoGUIController) loader.getController();
    			noleggioInCorsoController.setAbbonamento(abbonamento);
    			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    			window.setScene(scene);
    			window.show();
    		} catch(IOException e) {
    			e.printStackTrace();
    		}
    	} else {
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("EffettuaUnNoleggio.fxml"));
    			Parent mainChoiceParent = loader.load();
    			Scene scene = new Scene(mainChoiceParent);
    			EffettuaUnNoleggioGUIController effettuaUnNoleggioController = (EffettuaUnNoleggioGUIController) loader.getController();
    			effettuaUnNoleggioController.setAbbonamento(abbonamento);
    			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    			window.setScene(scene);
    			window.show();
    		} catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * Questo metodo cambia la scena attuale e porta l'utente alla schermata degli strumenti amministrativi.
     */
    @FXML
    public void goToStrumentiAmministrativi(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("StrumentiAmministrativi.fxml"));
			Parent mainChoiceParent = loader.load();
			Scene scene = new Scene(mainChoiceParent);
			StrumentiAmministrativiGUIController strumentiAmministrativiController = (StrumentiAmministrativiGUIController) loader.getController();
			strumentiAmministrativiController.setAbbonamento(abbonamento);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
}