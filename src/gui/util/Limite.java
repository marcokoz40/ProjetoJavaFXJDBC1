package gui.util;

import javafx.scene.control.TextField;

public class Limite {
	
	public static void setTextFieldInteger(TextField texto) {
		texto.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if (valorNovo != null && !valorNovo.matches("\\d*")) {
				texto.setText(valorAntigo);
			}
		});
	}
	
	public static void setTextFieldMaxLength(TextField texto, int max) {
		texto.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if (valorNovo != null && valorNovo.length() > max) {
				texto.setText(valorAntigo);
			}
		});
	}
	
	public static void setTextFieldDouble(TextField texto) {
		texto.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if (valorNovo != null && !valorNovo.matches("\\d*([\\.]\\d*)?")) {
				texto.setText(valorAntigo);
			}
		});
	}

}
