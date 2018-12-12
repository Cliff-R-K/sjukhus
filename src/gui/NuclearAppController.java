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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.SearchController;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class NuclearAppController implements Initializable {

	public TableView<RegRadio> tableview = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButton = new Button();
	public Button logOutButton1 = new Button();
	public Button searchButton = new Button();
	public Button clearButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();
	
	private List<RegRadio> sortedList;

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	public DatePicker startSortDate = new DatePicker();
	public DatePicker endSortDate = new DatePicker();

	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();
	
	public ComboBox<User> combobox_user = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio2 = new ComboBox<>();
	public ComboBox<Room> combobox_room2 = new ComboBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField text_kalibreringsaktivitet = new TextField();
	public TextField text_kalibreringstid = new TextField();
	public TextField text_batchnr = new TextField();
	public TextField text_kommentar = new TextField();
	public ListView<String> listView = new ListView<String>();

	public CheckBox check_kontamineringskontroll = new CheckBox();
	public TableView<RegRadio> radioView = new TableView<RegRadio>();
	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn substanceCol2 = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	private TableColumn uniqueIdCol = new TableColumn();
	public SearchController searchController;
	//private RegRadio regP;
	private User user;
	//private Date startdate;
	//private Date enddate;
	//private Date arrivalDate;
	//private Date startSortDate;
	//private Date endSortDate;
	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();
	
	private RegRadio chosenRegRadio;

	private ActionEvent event;

	// Test

	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> radiopharmaceuticalCol;

	///// ***********

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
	//FLIK 2
	public void addRoomsFlik() {
		combobox_room2.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}
	public void addUsersFlik() {
		combobox_user.getItems().addAll(FXCollections.observableArrayList(new UserDao().getAll()));
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
	//FLIK 2
	public void addProductsFlik() {
		combobox_radio2.getItems().addAll(FXCollections.observableArrayList(new RadiopharmaceuticalDao().getAll()));
	}

	public void addUser() {
		user = new UserDao().getCurrent(1);
	}

	public void logOutButtonAction(ActionEvent logout) throws Exception {
		this.event = logout;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Logga ut");
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

	public void searchButtonAction(ActionEvent search) throws Exception {
		this.event = search;
		searchRegRadioList.clear();
		searchRegRadioList.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDate(), getEndSortDate()));
		radioView.setItems(searchRegRadioList);
	}
	
	public void clearButton(ActionEvent search) throws Exception {
		this.event = search;
		searchRegRadioList.clear();
		radioView.setItems(searchRegRadioList);
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
		addRoomsFlik();
		addUsersFlik();
		addProductsFlik();
		signatur.setText(user.getSignature());
		ankomstdatum.setValue(LocalDate.now());
		combobox_radio.setDisable(true);

		////////////////////////////////////////////////

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

		searchRegRadioList.clear();
		radioView.setItems(searchRegRadioList);

		///////////////////////////////////////////////////
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
				label_rad_substance.setText(newValue.getSubstance().getName());
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
			/*
			RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(), text_batchnr.getText(),
					getContaminationControl(), combobox_radio.getValue(), combobox_room.getValue(), user, null,
					combobox_suppliers.getValue());


			if(regRadioList.size() >= 10)
				regRadioList.remove(regRadioList.size()-1);
			regRadioList.add(0, rr);
			searchRegRadioList.add(0, rr);
			tableview.setItems(regRadioList);
			radioView.getItems().add(0, rr);
			new RegRadioDao().save(rr);
			*/
		});
		///FLIK 2
		combobox_radio2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		});
		
	}

	private void saveProductButton() {
		new Thread() {
			@Override
			public void run() {
				RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(),
						text_batchnr.getText(), getContaminationControl(), combobox_radio.getValue(),
						combobox_room.getValue(), user, null, combobox_suppliers.getValue());

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
		regRadioList = FXCollections.observableArrayList(searchRegRadioList.subList(0, 10)); 
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

  public void clickedSearchScrollPane() {
		System.out.println("clicked scrollpane");
    
		chosenRegRadio = (RegRadio) radioView.getSelectionModel().getSelectedItem();
		chosenRegRadio.print();
	}
  	public Date getStartSortDate() {
		return java.sql.Date.valueOf(startSortDate.getValue());
  }
	public Date getEndSortDate() {
		return java.sql.Date.valueOf(endSortDate.getValue());
	}
}
