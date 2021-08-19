package gui;

import dati.ConnessioneDb;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Applicazione extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
	        ConnessioneDb db = ConnessioneDb.getIstance();
	        try {
	        	db.connetti();
	        	System.out.println("@@@ Connesso al database PostgreSQL! @@@\n");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}