package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modelo.servicos.DepartamentoServico;

public class TelaPrincipalController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemVendedor() {
		System.out.println("Vendedor");
	}

	@FXML
	public void onMenuItemDepartamento() {
		carregaTela("/gui/ListaDepartamentos.fxml", (ListaDepartamentoController controller) -> {
			controller.setDepServico(new DepartamentoServico());
			controller.atualizaTabDep();
		});
	}

	@FXML
	public void onMenuItemSobre() {
		carregaTela("/gui/Sobre.fxml", x -> {});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	private synchronized <T> void carregaTela(String nomeAbsoluto, Consumer<T> cons) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox novaVBox = loader.load();
			Scene cenaPrincipal = Main.getCenaPrincipal();
			VBox vBoxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();
			Node menuPrincipal = vBoxPrincipal.getChildren().get(0);
			vBoxPrincipal.getChildren().clear();
			vBoxPrincipal.getChildren().add(menuPrincipal);
			vBoxPrincipal.getChildren().addAll(novaVBox.getChildren());
			T controller = loader.getController();
			cons.accept(controller);
		}
		catch (IOException e) {
			Alerta.mostrarAlerta("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}
	
	

}
