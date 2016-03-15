package net.javierjimenez.Colors;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;

public class ColorsController {
	@FXML
	private ComboBox<String> selectorIdioma;
	@FXML
	private Button busquedaColor;
	@FXML
	private Label color1;
	@FXML
	private Label idioma1;
	@FXML
	private Label idioma2;
	@FXML
	private Label idioma3;
	@FXML
	private Label color2;
	@FXML
	private Label color3;

	// Event Listener on ComboBox[#selectorIdioma].onAction
	@FXML
	public void seleccionarIdioma(MouseEvent event) {
		selectorIdioma.getItems().addAll("castella", "nom", "angles", "frances");
	}

	// Event Listener on Button[#busquedaColor].onAction
	@FXML
	public void buscarColorIdiomas(ActionEvent event) {
		// TODO Autogenerated
	}
}
