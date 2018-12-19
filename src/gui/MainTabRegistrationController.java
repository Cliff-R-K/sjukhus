package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.security.jgss.ExtendedGSSContext;

import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class MainTabRegistrationController implements Initializable {

	public TableView<RegRadio> tableview = new TableView<>();

	public Button saveButton = new Button();
	public Button logOutButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();

	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField text_kalibreringsaktivitet = new TextField();
	public TextField text_kalibreringstid = new TextField();
	public TextField text_batchnr = new TextField();
	public TextField text_kommentar = new TextField();
	public ListView<String> listView = new ListView<String>();

	public CheckBox check_kontamineringskontroll = new CheckBox();

	private User user;

	private int tabOneNumberOfRows = 30;
	private ActionEvent event;
	
	@FXML
	private TabStorageController tabStorageController;
	
	@FXML
	private TabKasseringController tabKasseringController;

	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> radiopharmaceuticalCol;
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatum;
	@FXML
	TableColumn<RegRadio, Double> columnActivity;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplier;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceutical;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdate;
	@FXML
	TableColumn<RegRadio, String> columnTime;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumber;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControl;
	@FXML
	TableColumn<RegRadio, Room> columnRoom;
	@FXML
	TableColumn<RegRadio, User> columnUser;

	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}

	public void addRooms() {
		combobox_room.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}

	public void addProducts() {
		combobox_radio.setDisable(false);
		radioList.clear();
		radioList.addAll(new RadiopharmaceuticalDao()
				.getRadiopharmaceuticalsBySupplierName(combobox_suppliers.getValue().toString()));
		combobox_radio.getItems().clear();
		combobox_radio.getItems().addAll(radioList);
		combobox_radio.getSelectionModel().selectFirst();
	}

	public void addUser() {
		user = new UserDao().getCurrent(1);
	}

	public void logOutButtonAction(ActionEvent logout) throws Exception {
		this.event = logout;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logga ut");
		alert.setHeaderText(null);
		alert.setContentText("Vill du fortsätta?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			logOut();
		}
	}

	public void logOut() throws IOException {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("LOGIN.fxml"));
		primaryStage.setTitle("Login");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addSuppliers();
		addRooms();
		addUser();
		signatur.setText(user.getSignature());
		ankomstdatum.setValue(LocalDate.now());
		combobox_radio.setDisable(true);
		
		//tabStorageController.populateListFromDatabase();


		setUpTableView();
		new Thread(() -> populateListFromDatabase()).start();

		combobox_suppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			combobox_radio.getItems().clear();
			combobox_radio.getItems().addAll(FXCollections.observableArrayList(
					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			combobox_radio.setDisable(false);
			combobox_radio.getSelectionModel().selectFirst();
		});

		combobox_radio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {

				//label_rad_substance.setText(newValue.getSubstance().getName());
				label_halftime.setText(newValue.getSubstance().getHalfLife() + "");
			}
		});

		text_kalibreringstid.focusedProperty().addListener((observable, oldText, newText) -> {
			if (!newText) {
				if (!text_kalibreringstid.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):?[0-5][0-9]$")) {
					text_kalibreringstid.setText("");
					text_kalibreringstid.setPromptText("Felaktig tid");
				}
			}
		});
		check_kontamineringskontroll.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				text_kommentar.setDisable(true);
				text_kommentar.clear();
			} else {
				text_kommentar.setDisable(false);
			}
		});
		saveButton.setOnAction((event) -> {

			saveProductButton();

		});
	}

	private void saveProductButton() {

		new Thread() {
			@Override
			public void run() {
				RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(),
						text_batchnr.getText(), getContaminationControl(), combobox_radio.getValue(),
						combobox_room.getValue(), user, combobox_suppliers.getValue());

				columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
				columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
				columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
				columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
				columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
				columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
				columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
				columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
				columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));

				regRadioList.add(0, rr);
				searchRegRadioList.add(0, rr);
				tableview.setItems(regRadioList);
				new RegRadioDao().save(rr);
			}
		}.start();
	}

	public void populateListFromDatabase() {
		searchRegRadioList.addAll(new RegRadioDao().getAll());
		populateTabOneTablelist();
	}

	public void populateTabOneTablelist() {
		if(searchRegRadioList.size() > tabOneNumberOfRows)
			regRadioList = FXCollections.observableArrayList(searchRegRadioList.subList(0, tabOneNumberOfRows)); 
		else {
			regRadioList = searchRegRadioList;
		}
		tableview.getItems().addAll(regRadioList);

	}

	public double getActivity() {
		return Double.parseDouble(text_kalibreringsaktivitet.getText().replace(",", "."));
	}

	public LocalDateTime getCalibrationDate() {
		LocalDate date = kalibreringsdatum.getValue();
		LocalTime time = LocalTime.parse(getTime(), DateTimeFormatter.ofPattern("HHmm"));
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
	}

	public void setUpTableView() {
		columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));

		columnSupplier.setCellFactory(ComboBoxTableCell.forTableColumn(supplierList));	
	}

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(ankomstdatum.getValue());
	}

	public String getContaminationControl() {
		return check_kontamineringskontroll.isSelected() ? "OK" : text_kommentar.getText();
	}

	public String getTime() {
		String time = text_kalibreringstid.getText();
		return time.replace(":", "");
	}
}

