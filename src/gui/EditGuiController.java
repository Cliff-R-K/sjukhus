package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import dataholder.DataHolder;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class EditGuiController implements Initializable {

	public TableView<RegRadio> tableView = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> editRegRadioList = FXCollections.observableArrayList();

	public DatePicker arrivalDatePicker = new DatePicker();
	public DatePicker calibrationDatePicker = new DatePicker();

	public ComboBox<Supplier> comboBoxSuppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> comboBoxRadios = new ComboBox<>();
	public ComboBox<Room> comboBoxRooms = new ComboBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField startActivityTextField = new TextField();
	public TextField calibrationTextField = new TextField();
	public TextField calibrationTimeTextField = new TextField();
	public TextField batchNrTextField = new TextField();
	public TextField commentaryTextField = new TextField();
	public ListView<String> listView = new ListView<String>();

	public CheckBox checkBoxContamination = new CheckBox();

	private User user;
	///////////////////////////////////////////////////////////
	public TableView radioView = new TableView<RegRadio>();
	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();
	public TableColumn uniqueIdCol = new TableColumn();

	public TableView radioViewOld = new TableView<RegRadio>();
	public TableColumn startActivityColOld = new TableColumn();
	public TableColumn roomColOld = new TableColumn();
	public TableColumn startDateColOld = new TableColumn();
	public TableColumn calibrationColOld = new TableColumn();
	public TableColumn arrivalDateColOld = new TableColumn();
	public TableColumn batchNumberColOld = new TableColumn();
	public TableColumn userColOld = new TableColumn();;
	public TableColumn radioPharmaceuticalColOld = new TableColumn();
	public TableColumn endDateColOld = new TableColumn();;
	public TableColumn contaminationControllColOld = new TableColumn();
	public TableColumn supplierColOld = new TableColumn();
	public TableColumn uniqueIdColOld = new TableColumn();

	public Button editButton = new Button();
	public Button closeButton = new Button();
	public Button saveEditButton = new Button();
	private RegRadio chosenRegRadio;
	//	private RegRadio regP;
	//	private Date startdate;
	//	private Date enddate;
	//	private Date arrivalDate;

	//	private ActionEvent event;
	//////////////////////////////////////////////////////////

	// Test

	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> radiopharmaceuticalCol;

	///// *************

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

	private ActionEvent event;

	private RegRadio newRegRadio;

	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		comboBoxSuppliers.getItems().addAll(supplierList);
	}

	public void addRooms() {
		comboBoxRooms.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}

	public void addProducts() {
		comboBoxRadios.setDisable(false);
		radioList.clear();
		radioList.addAll(new RadiopharmaceuticalDao()
				.getRadiopharmaceuticalsBySupplierName(comboBoxSuppliers.getValue().toString()));
		comboBoxRadios.getItems().clear();
		comboBoxRadios.getItems().addAll(radioList);
		comboBoxRadios.getSelectionModel().selectFirst();
	}

	public void addUser() {
		user = new UserDao().getCurrent(1);
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
		//runTempStorage();
		signatur.setText(user.getSignature());
		arrivalDatePicker.setValue(LocalDate.now());
		comboBoxRadios.setDisable(true);
		System.out.println("init");
		////////////////////////////////////////////////
		//		FXMLLoader	 fLoader = new FXMLLoader();
		//		fLoader.setLocation(getClass().getResource("UI.fxml"));
		//		try {
		//			System.out.println("zzzzzzzzzzzzzzzzzzzzz");
		//			fLoader.load();
		//		} catch (IOException ex) { 
		//			Logger.getLogger(NuclearAppController.class.getName(), null).log(Level.SEVERE,null,ex);
		//		}
		//		NuclearAppController nuclearController = fLoader.getController();
		chosenRegRadio=DataHolder.getSavedRadio();
		System.out.println("qqqqqqqqqqqqqqqqqq");
		chosenRegRadio.print();
		System.out.println("qqqqqqqqqqqqqqqqqq");


		startActivityCol.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
		radiopharmaceuticalCol.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		calibrationCol.setCellValueFactory(new PropertyValueFactory<>("calibration"));
		arrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		batchNumberCol.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		contaminationControllCol.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
		uniqueIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		editRegRadioList.add(chosenRegRadio);
		radioView.setItems(editRegRadioList);

		startActivityColOld.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		roomColOld.setCellValueFactory(new PropertyValueFactory<>("room"));
		radioPharmaceuticalColOld.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		startDateColOld.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateColOld.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		calibrationColOld.setCellValueFactory(new PropertyValueFactory<>("calibration"));
		arrivalDateColOld.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		batchNumberColOld.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		supplierColOld.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		contaminationControllColOld.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		userColOld.setCellValueFactory(new PropertyValueFactory<>("user"));
		uniqueIdColOld.setCellValueFactory(new PropertyValueFactory<>("id"));


		tableView.setItems(editRegRadioList);


		///////////////////////////////////////////////////

		comboBoxSuppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			comboBoxRadios.getItems().clear();
			comboBoxRadios.getItems().addAll(FXCollections.observableArrayList(
					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			comboBoxRadios.setDisable(false);
			comboBoxRadios.getSelectionModel().selectFirst();
		});

		comboBoxRadios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				label_rad_substance.setText(newValue.getSubstance().getName());
				label_halftime.setText(newValue.getSubstance().getHalfLife() + "");
			}
		});

		calibrationTimeTextField.focusedProperty().addListener((observable, oldText, newText) -> {
			if (!newText) {
				if (!calibrationTimeTextField.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):?[0-5][0-9]$")) {
					calibrationTimeTextField.setText("");
					calibrationTimeTextField.setPromptText("Felaktig tid");
				}
			}
		});
		checkBoxContamination.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				commentaryTextField.setDisable(true);
				commentaryTextField.clear();
			} else {
				commentaryTextField.setDisable(false);
			}
		});

		saveButton.setOnAction((event) -> {


			//			RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(), text_batchnr.getText(),
			//					getContaminationControl(), combobox_radio.getValue(), combobox_room.getValue(), user, null,
			//					combobox_suppliers.getValue());
			//
			//			columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
			//			columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
			//			columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
			//			columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
			//			columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
			//			columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
			//			columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
			//			columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
			//			columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));

			//			regRadioList.add(0, rr);
			//			tableview.setItems(regRadioList);
			//			new RegRadioDao().save(rr);

		});

	}

	public void runTempStorage() {
		new Thread() {
			@Override
			public void run() {
				editRegRadioList.addAll(new RegRadioDao().getAll());
			}
		}.start();
	}

	public double getActivity() {
		return Double.parseDouble(calibrationTextField.getText().replace(",", "."));
	}

	public LocalDateTime getCalibrationDate(){
		LocalDate date = calibrationDatePicker.getValue();
		LocalTime time = LocalTime.parse(getTime(), DateTimeFormatter.ofPattern("HHmm"));
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
	}

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(arrivalDatePicker.getValue());
	}

	public String getContaminationControl() {
		return checkBoxContamination.isSelected() ? "OK" : commentaryTextField.getText();
	}

	public String getTime() {
		String time = calibrationTimeTextField.getText();
		return time.replace(":", "");
	}


	public void closeEditGui(ActionEvent event) throws Exception {

		System.out.println("close");
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		// do what you have to do
		stage.close();
		System.out.println("end close");
	}
	public void clickedSaveEdit() {

		newRegRadio = new RegRadio(Double.valueOf(startActivityTextField.getText()), null, null, null, null, null, null, user, null);

	}
	public void setNewInfo() {
		
		
		
	}
}


