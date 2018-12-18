package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sun.istack.internal.logging.Logger;

import controller.WriteToExcelController;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import dataholder.DataHolder;
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
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
	public Button editButton = new Button();
	public Button writeToExcelButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioListTabTwo = FXCollections.observableArrayList();

	private List<RegRadio> sortedList;

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	public DatePicker startSortDate = new DatePicker();
	public DatePicker endSortDate = new DatePicker();

	private java.sql.Date start;
	private java.sql.Date end;

	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();

	public ComboBox<User> combobox_user_tab_two = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio_tab_two = new ComboBox<>();
	public ComboBox<Room> combobox_room_tab_two = new ComboBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField text_kalibreringsaktivitet = new TextField();
	public TextField text_kalibreringstid = new TextField();
	public TextField text_batchnr = new TextField();
	public TextField text_kommentar = new TextField();
	public ListView<String> listView = new ListView<String>();

	public CheckBox check_kontamineringskontroll = new CheckBox();

	///////////////////////////////////////////////////////////
	public TableView radioView = new TableView<RegRadio>();
	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn substanceCol2 = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	public TableColumn uniqueIdCol = new TableColumn();

	private User user;

	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();

	private RegRadio chosenRegRadio;
	private Radiopharmaceutical radio_tab_two = null;
	private Room room_tab_two = null;
	private User user_tab_two;
	private int tabOneNumberOfRows = 30;
	private ActionEvent event;

	private ArrayList<String> columnHeaderList = new ArrayList<>();


	//////////////////////////////////////////////////////////

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


	private RegRadio radioToEdit;

	private int i;

	private Window primaryStage;

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
	//FLIK 2
	public void addProductsTabTwo() {
		radioListTabTwo.clear();
		radioListTabTwo.addAll(new RadiopharmaceuticalDao().getAll());
		combobox_radio_tab_two.getItems().addAll(radioListTabTwo);
	}
	//FLIK 2
	public void addRoomsTabTwo() {
		combobox_room_tab_two.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}
	//FLIK 2
	public void addUsersTabTwo() {
		combobox_user_tab_two.getItems().addAll(FXCollections.observableArrayList(new UserDao().getAll()));
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

	public void clearButton(ActionEvent search) throws Exception {
		this.event = search;
		combobox_room_tab_two.getSelectionModel().clearSelection();
		combobox_radio_tab_two.getSelectionModel().clearSelection();
		combobox_user_tab_two.getSelectionModel().clearSelection();
		endSortDate.setValue(LocalDate.now());
		startSortDate.setValue(LocalDate.of(1900, 01, 01));
		editButton.setDisable(true);
		searchRegRadioList.clear();
		populateListFromDatabase();
		radioView.setItems(searchRegRadioList);
		radioView.refresh();
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

		addRoomsTabTwo();
		addUsersTabTwo();
		addProductsTabTwo();

		signatur.setText(user.getSignature());
		ankomstdatum.setValue(LocalDate.now());
		endSortDate.setValue(LocalDate.now());
		startSortDate.setValue(LocalDate.of(1900, 01, 01));
		combobox_radio.setDisable(true);



		setUpTableView();
		setUpTableViewTabTwo();
		new Thread(() -> populateListFromDatabase()).start();

		////////////////////////////////////////////////



		///////////////////////////////////////////////////



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
		///FLIK 2
		combobox_radio_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				radio_tab_two = newValue;
			} else {
				radio_tab_two = null;
			}
		});
		combobox_room_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				room_tab_two = newValue;
			} else {
				radio_tab_two = null;
			}
		});
		combobox_user_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				user_tab_two = newValue;
			} else {
				user_tab_two = null;
			}
		});
		//FLIK 2
		editButton.setDisable(true);
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
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
	public void setUpTableViewTabTwo() {
		uniqueIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		radiopharmaceuticalCol.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		batchNumberCol.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		startActivityCol.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		arrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		contaminationControllCol.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
		calibrationCol.setCellValueFactory(new PropertyValueFactory<>("calibrationInfo"));
		userCol.setCellValueFactory(new PropertyValueFactory<>("user"));


		searchRegRadioList.clear();
		radioView.setItems(searchRegRadioList);
		addColumnNamesToList();
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
		editButton.setDisable(false);
		System.out.println("clicked scrollpane");
		chosenRegRadio = (RegRadio) radioView.getSelectionModel().getSelectedItem();
		i = chosenRegRadio.getId();
		chosenRegRadio.print();
	}

	public void clickedActivityButton(ActionEvent logout) throws Exception {
		System.out.println("clicked activity");
		DataHolder.setSavedRadio(chosenRegRadio);
		Parent root = FXMLLoader.load(getClass().getResource("ActivityControl.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Aktivitetskontroll");
		stage.setScene(new Scene(root));
		stage.show();
	}


	public Date getStartSortDate() {
		start = java.sql.Date.valueOf(startSortDate.getValue());
		return start;
	}

	public Date getEndSortDate() {
		end = java.sql.Date.valueOf(endSortDate.getValue());
		return end;
	}

	public void searchButtonAction(ActionEvent search) throws Exception {
		this.event = search;
		searchRegRadioList.clear();
		searchRegRadioList.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDate(), getEndSortDate(), radio_tab_two, room_tab_two, user_tab_two));
		radioView.setItems(searchRegRadioList);

	}
	public void writeTableViewToExcel() {

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(null);
		
		if (file != null) {
			String ebola = file.toPath().toString();
			System.out.println(ebola);
			WriteToExcelController writeExCon = new WriteToExcelController();
			writeExCon.execute(radioView, columnHeaderList,file);
		}
	}
	
	private void addColumnNamesToList() {

		columnHeaderList.add(uniqueIdCol.getText());
		columnHeaderList.add(supplierCol.getText());
		columnHeaderList.add(radiopharmaceuticalCol.getText());
		columnHeaderList.add(batchNumberCol.getText());
		columnHeaderList.add(startActivityCol.getText());
		columnHeaderList.add(startDateCol.getText());
		columnHeaderList.add(arrivalDateCol.getText());
		columnHeaderList.add(contaminationControllCol.getText());
		columnHeaderList.add(roomCol.getText());
		columnHeaderList.add(calibrationCol.getText());
		columnHeaderList.add(userCol.getText());

	}



}

