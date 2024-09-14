package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Teacher;
import model.services.TeacherService;

public class TeacherListController implements Initializable, DataChangeListener {

	private TeacherService service;

	@FXML
	private TableView<Teacher> tableViewTeacher;

	@FXML
	private TableColumn<Teacher, Integer> tableColunmId;

	@FXML
	private TableColumn<Teacher, String> tableColunmName;

	@FXML
	private TableColumn<Teacher, String> tableColunmCpf;
	@FXML
	private TableColumn<Teacher, String> tableColunmPhone;

	@FXML
	private TableColumn<Teacher, Date> tableColunmAdmissionDate;

	@FXML
	private TableColumn<Teacher, Double> tableColunmSalary;

	@FXML
	private TableColumn<Teacher, String> tableColunmChief;

	@FXML
	private TableColumn<Teacher, String> tableColunmCoordinator;

	@FXML
	private TableColumn<Teacher, Teacher> tableColumnEDIT;

	@FXML
	private TableColumn<Teacher, Teacher> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Teacher> obsList;

	@FXML
	public void onbtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Teacher obj = new Teacher();
		createDialogForm(obj, "/gui/TeacherForm.fxml", parentStage);
	}

	public void setTeacherService(TeacherService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColunmCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColunmPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tableColunmAdmissionDate.setCellValueFactory(new PropertyValueFactory<>("admissionDate"));
		tableColunmSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		tableColunmChief.setCellValueFactory(new PropertyValueFactory<>("chief"));
		tableColunmCoordinator.setCellValueFactory(new PropertyValueFactory<>("coordinator"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewTeacher.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null)
			throw new IllegalStateException("Service was null!");

		List<Teacher> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewTeacher.setItems(obsList);
		initEditButtons();
	}

	private void createDialogForm(Teacher obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			TeacherFormController controller = loader.getController();
			controller.setTeacher(obj);
			controller.setTeacherService(new TeacherService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Teacher data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Teacher, Teacher>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Teacher obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/TeacherForm.fxml", Utils.currentStage(event)));
			}
		});
	}
}
