package net.javierjimenez.Colors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class ColorController implements Initializable {
	@FXML
	private ComboBox<String> selectorIdioma = new ComboBox<>();
	@FXML
	private Button busquedaColor;
	@FXML
	private Label idioma1;
	@FXML
	private Label idioma2;
	@FXML
	private Label idioma3;
	@FXML
	private Label color1;
	@FXML
	private Label color2;
	@FXML
	private Label color3;
	@FXML
	private TextField textIdioma;

	private Connection conn = null;

	private String idioma = null;

	@FXML
	public void seleccionarIdioma(ActionEvent event) {
		idioma = selectorIdioma.getValue().toString();
	}

	@FXML
	public void buscarColorIdiomas(ActionEvent event) {
		Statement cerca = null;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://192.168.4.1/traductor", "foot", "ball");
			cerca = conn.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ResultSet resultat = null;
		
		try {
			
			resultat = cerca.executeQuery("SELECT * FROM colors WHERE " + idioma + " = '" + textIdioma.getText() + "'");
			
			int [] posicions = new int [3];
			
			int j = 0;
			
			if(resultat.next()){
				
				for(int i = 0; i < selectorIdioma.getItems().size(); i++){
					
					if(selectorIdioma.getItems().get(i) != idioma){
						posicions[j] = i;
						j++;
					}
				}
				
				idioma1.setText(selectorIdioma.getItems().get(posicions[0]));
				color1.setText(resultat.getString(posicions[0] + 2));
				idioma2.setText(selectorIdioma.getItems().get(posicions[1]));
				color2.setText(resultat.getString(posicions[1] + 2));
				idioma3.setText(selectorIdioma.getItems().get(posicions[2]));
				color3.setText(resultat.getString(posicions[2] + 2));
				
			} else {
				System.out.println("Ops!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectorIdioma.getItems().addAll("angles", "castella", "nom", "frances");

		if (selectorIdioma.getValue() == null) {

			selectorIdioma.setValue("angles");
			idioma = selectorIdioma.getValue().toString();
		}
	}
}
