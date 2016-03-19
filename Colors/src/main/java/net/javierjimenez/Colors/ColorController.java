package net.javierjimenez.Colors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

/**
 * 
 * @author Surrui
 *
 */
public class ColorController implements Initializable {

	/**
	 * Objeto ComboBox que contiene los diferentes idiomas a elegir.
	 */
	@FXML
	private ComboBox<String> selectorIdioma = new ComboBox<>();

	/**
	 * Objeto Button encargado de conectar con la BD y buscar la Query escrita.
	 */
	@FXML
	private Button busquedaColor;

	/**
	 * Objeto Label que contiene el String del nombre de uno de los idiomas
	 */
	@FXML
	private Label idioma1;

	/**
	 * Objeto Label que contiene el String del nombre de uno de los idiomas
	 */
	@FXML
	private Label idioma2;

	/**
	 * Objeto Label que contiene el String del nombre de uno de los idiomas
	 */
	@FXML
	private Label idioma3;

	/**
	 * Objeto Label que contiene el String del nombde del color traducido de uno
	 * de los idiomas restantes
	 */
	@FXML
	private Label color1;

	/**
	 * Objeto Label que contiene el String del nombde del color traducido de uno
	 * de los idiomas restantes
	 */
	@FXML
	private Label color2;

	/**
	 * Objeto Label que contiene el String del nombde del color traducido de uno
	 * de los idiomas restantes
	 */
	@FXML
	private Label color3;

	/**
	 * Objeto TextField donde escribiremos el nombre del color del que queremos
	 * saber sus traducciones.
	 */
	@FXML
	private TextField textIdioma;

	/**
	 * Objeto Connection encargado de establecer la conexion con la BD
	 */
	private Connection conn = null;

	/**
	 * Variable String que contendra el idioma del color que queremos traducir
	 */
	private String idioma = null;

	/**
	 * Metodo que guarda el idioma del color que queremos traducir
	 * 
	 * @param event
	 */
	@FXML
	public void seleccionarIdioma(ActionEvent event) {
		idioma = selectorIdioma.getValue().toString();
	}

	/**
	 * Metodo encargado de ejecutar la Query en nuestra base de datos y
	 * devolvernos el resultado o un Dialog indicando que el color que buscamos
	 * no existe en la base de datos.
	 * 
	 * @param event
	 */
	@FXML
	public void buscarColorIdiomas(ActionEvent event) {
		Statement cerca = null;

		try {

			conn = DriverManager.getConnection("jdbc:mysql://192.168.4.1/traductor", "foot", "ball");
			cerca = conn.createStatement();

		} catch (SQLException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Problema de conexión con BD");
			alert.setHeaderText("ERROR: Problema de conexión");
			alert.setContentText("Error al conectar con la base de datos.\nRevise si esta operativa.");

			alert.showAndWait();

			System.exit(0);

		}

		ResultSet resultat = null;

		try {

			resultat = cerca.executeQuery("SELECT * FROM colors WHERE " + idioma + " = '" + textIdioma.getText() + "'");

			int[] posicions = new int[3];

			int j = 0;

			if (resultat.next()) {

				for (int i = 0; i < selectorIdioma.getItems().size(); i++) {

					if (selectorIdioma.getItems().get(i) != idioma) {
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

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alerta: Busqueda infructuosa.");
				alert.setContentText("No existe ese color en la BD.");
				alert.showAndWait();

				textIdioma.setText("");
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

	/**
	 * Metodo encargado de añadir al ComboBox las diferentes opciones a elegir.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectorIdioma.getItems().addAll("angles", "castella", "nom", "frances");

		if (selectorIdioma.getValue() == null) {

			selectorIdioma.setValue("angles");
			idioma = selectorIdioma.getValue().toString();
		}
	}
}
