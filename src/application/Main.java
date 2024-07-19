package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene cenaPrincipal;
	
	@Override
	public void start(Stage palco) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipal.fxml"));
			ScrollPane scrollPane = loader.load();
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			cenaPrincipal = new Scene(scrollPane);
			palco.setScene(cenaPrincipal);
			palco.setTitle("Aplicação JavaFX JDBC");
			palco.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getCenaPrincipal() {
		return cenaPrincipal;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
