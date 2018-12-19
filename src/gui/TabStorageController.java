package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import controller.WriteToExcelController;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class TabStorageController implements Initializable {

	public TableView<RegRadio> tableview = new TableView<>();

	public Button logOutButton = new Button();
	public Button searchButton = new Button();
	public Button clearButton = new Button();
	public Button calibrationActivityButton = new Button();
	public Button writeToExcelButton = new Button();

	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioListTabTwo = FXCollections.observableArrayList();

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	public DatePicker startSortDate = new DatePicker();
	public DatePicker endSortDate = new DatePicker();

	private java.sql.Date startDate;
	private java.sql.Date endDate;

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

	public TableView<RegRadio> radioView = new TableView<>();
/*	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn substanceCol2 = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	//public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	public TableColumn uniqueIdCol = new TableColumn();
	//public TableColumn radiopharmaceuticalCol = new TableColumn();

	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();*/

	private RegRadio chosenRegRadio;
	private Radiopharmaceutical radio_tab_two = null;
	private Room room_tab_two = null;
	private User user_tab_two;
	private ActionEvent event;
	private ArrayList<String> columnHeaderList = new ArrayList<>();
	
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatum;
	@FXML
	TableColumn<RegRadio, Double> columnActivity;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplier;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceutical;
	@FXML
	TableColumn<RegRadio, String> columnCalibrationInfo;
	@FXML
	TableColumn<RegRadio, String> columnTime;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdate;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumber;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControl;
	@FXML
	TableColumn<RegRadio, Room> columnRoom;
	@FXML
	TableColumn<RegRadio, User> columnUser;
	@FXML
	TableColumn<RegRadio, Integer> columnID;
	
	public void setUpTableViewTabTwo() {
		columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnCalibrationInfo.setCellValueFactory(new PropertyValueFactory<>("calibrationInfo"));
		columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));
		searchRegRadioList.clear();
		radioView.setItems(searchRegRadioList);
		addColumnNamesToList();
	}
	
	public void addProductsTabTwo() {
		radioListTabTwo.clear();
		radioListTabTwo.addAll(new RadiopharmaceuticalDao().getAll());
		combobox_radio_tab_two.getItems().addAll(radioListTabTwo);
	}
	
	public void addRoomsTabTwo() {
		combobox_room_tab_two.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}
	
	public void addUsersTabTwo() {
		combobox_user_tab_two.getItems().addAll(FXCollections.observableArrayList(new UserDao().getAll()));
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
		calibrationActivityButton.setDisable(true);
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
		addRoomsTabTwo();
		addUsersTabTwo();
		addProductsTabTwo();

		ankomstdatum.setValue(LocalDate.now());
		endSortDate.setValue(LocalDate.now());
		startSortDate.setValue(LocalDate.of(1900, 01, 01));

		setUpTableViewTabTwo();
		new Thread(() -> populateListFromDatabase()).start();
		
		

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
	}

	public void populateListFromDatabase() {
		searchRegRadioList.addAll(new RegRadioDao().getAll());
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

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(ankomstdatum.getValue());
	}

	public String getTime() {
		String time = text_kalibreringstid.getText();
		return time.replace(":", "");
	}

	public void clickedSearchScrollPane() {
		calibrationActivityButton.setDisable(false);
		System.out.println("clicked scrollpane");
		chosenRegRadio = (RegRadio) radioView.getSelectionModel().getSelectedItem();
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
		startDate = java.sql.Date.valueOf(startSortDate.getValue());
		return startDate;
	}

	public Date getEndSortDate() {
		endDate = java.sql.Date.valueOf(endSortDate.getValue());
		return endDate;
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

		columnHeaderList.add(columnID.getText());
		columnHeaderList.add(columnSupplier.getText());
		columnHeaderList.add(columnRadiopharmaceutical.getText());
		columnHeaderList.add(columnBatchNumber.getText());
		columnHeaderList.add(columnActivity.getText());
		columnHeaderList.add(columnCalibrationdate.getText());
		columnHeaderList.add(columnAnkomstdatum.getText());
		columnHeaderList.add(columnContaminationControl.getText());
		columnHeaderList.add(columnRoom.getText());
		columnHeaderList.add(columnCalibrationInfo.getText());
		columnHeaderList.add(columnUser.getText());
	}
}