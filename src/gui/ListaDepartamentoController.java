package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.entidades.Departamento;
import modelo.servicos.DepartamentoServico;

public class ListaDepartamentoController implements Initializable {
	
	private DepartamentoServico depServico;
	private ObservableList<Departamento> obsList;	
	
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;
	
	@FXML
	private Button btNovo;
	
	@FXML
	public void onBtNovo() {
		System.out.println("Botão clicado");
	}
	
	public void setDepServico(DepartamentoServico depServico) {
		this.depServico = depServico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		iniciarComponentes();
		
	}

	private void iniciarComponentes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		Stage palco = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(palco.heightProperty());
		
	}
	
	public void atualizaTabDep() {
		if (depServico == null) {
			throw new IllegalStateException("O serviço está nulo");
		}
		List<Departamento> listaDep = depServico.buscaTodos();
		obsList = FXCollections.observableArrayList(listaDep);
		tableViewDepartamento.setItems(obsList);
	}

}
