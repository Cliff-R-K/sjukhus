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
import dao.SubstanceDao;
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
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumnBase;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Substance;
import model.Supplier;
import model.User;

public class NuclearAppController implements Initializable {

	public TableView<RegRadio> tableview = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButtonTab1 = new Button();
	public Button logOutButtonTab2 = new Button();
	public Button searchButtonTab2 = new Button();
	public Button clearButtonTab2 = new Button();
	public Button editButtonTab2 = new Button();
	public Button writeToExcelButtonTab2 = new Button();
	public Button searchButtonTab3 = new Button();
	public Button clearButtonTab3 = new Button();
	public Button logOutButtonTab3 = new Button();
	public Button editButtonTab3 = new Button();
	public Button writeToExcelButtonTab3 = new Button();
	public Button discardButton = new Button();

	//    @FXML public Tab creationTab;
	//    // Inject controller
	//    @FXML public CreationTabController creationTabController;


	public Button aboutButton = new Button();
	public Button helpButtonTab1 = new Button();
	public Button helpButtonTab2 = new Button();
	public Button helpButtonTab3 = new Button();


	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Substance> substanceList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioListTab2 = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioListTab3 = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioListTabTwo = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioListTabThree = FXCollections.observableArrayList();
	private ObservableList<String> formList = FXCollections.observableArrayList();
	private ObservableList<String> roomActivityList = FXCollections.observableArrayList();
	private ObservableList<Room> roomList = FXCollections.observableArrayList();



	private List<RegRadio> sortedList;

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	public DatePicker startSortDateTab2 = new DatePicker();
	public DatePicker endSortDateTab2 = new DatePicker();
	public DatePicker startSortDateTab3 = new DatePicker();
	public DatePicker endSortDateTab3 = new DatePicker();

	private java.sql.Date start;
	private java.sql.Date end;

	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();

	public ComboBox<User> combobox_user_tab_two = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio_tab_two = new ComboBox<>();
	public ComboBox<Room> combobox_room_tab_two = new ComboBox<>();

	public ComboBox<User> combobox_user_tab_three = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio_tab_three = new ComboBox<>();
	public ComboBox<Room> combobox_room_tab_three = new ComboBox<>();



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
	public TableView searchRadioViewTab2 = new TableView<RegRadio>();
	public TableView searchRadioViewTab3 = new TableView<RegRadio>();


	////////TAB4//////////////////


	public ComboBox<String> typeComboBoxTabFour = new ComboBox<>();
	public ComboBox<Supplier> selectSupplierComboBoxTabFour = new ComboBox<>();
	public ComboBox<Substance> selectSubstanceComboBoxTabFour = new ComboBox<>();
	public ComboBox<String> thirdComboBox = new ComboBox<>();

	public TextField firstTextFieldTabFour = new TextField();
	public TextField secondTextFieldTabFour = new TextField();

	public Button saveButtonTabFour = new Button();
	public Button cancelButtonTabFour = new Button();

	public ArrayList<String> listOfTypesTabFour = new ArrayList<String>();

	public Label firstComboBoxAttributeLabelTabFour = new Label();
	public Label secondComboBoxAttributeLabelTabFour = new Label();
	public Label firstTextFieldAttributeLabelTabFour = new Label();
	public Label secondTextFieldAttributeLabelTabFour = new Label();
	public Label thirdTextFieldAttributeLabelTabFour = new Label();
	public Label thirdComboBoxAttributeLabelTabFour = new Label();

	public TextArea feedBackTextAreaTabFour = new TextArea();


	///////////TAB4//////////////////


	private User user;

	private RegRadio chosenRegRadioTab2;
	private RegRadio chosenRegRadioTab3;
	private Radiopharmaceutical radio_tab_two = null;
	private Radiopharmaceutical radio_tab_three = null;
	private Room room_tab_two = null;
	private Room room_tab_three = null;
	private User user_tab_two;
	private User user_tab_three;
	private int tabOneNumberOfRows = 10;
	private ActionEvent event;

	private ArrayList<String> columnHeaderList = new ArrayList<>();
	public Image aboutIcon = new Image(getClass().getResourceAsStream("/icons/icons8-trash.png"),16,16,true,true);


	//////////////////////////////////////////////////////////

	/****************************** TAB 1 ****************************************/
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatumTab1;
	@FXML
	TableColumn<RegRadio, Double> columnActivityTab1;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplierTab1;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceuticalTab1;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdateTab1;
	@FXML
	TableColumn<RegRadio, String> columnTimeTab1;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumberTab1;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControlTab1;
	@FXML
	TableColumn<RegRadio, Room> columnRoomTab1;
	@FXML
	TableColumn<RegRadio, User> columnUserTab1;
	@FXML
	TableColumn<RegRadio, Integer> columnIdTab1;
	TableColumn<RegRadio, String> editColumn = new TableColumn<>();
	private RegRadio radioToEdit;
	int currentRowIndex;
	private RegRadio oldRegRadio;
	HBox buttons;
	/****************************** TAB 2 ****************************************/
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatumTab2;
	@FXML
	TableColumn<RegRadio, Double> columnActivityTab2;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplierTab2;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceuticalTab2;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdateTab2;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumberTab2;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControlTab2;
	@FXML
	TableColumn<RegRadio, Room> columnRoomTab2;
	@FXML
	TableColumn<RegRadio, User> columnUserTab2;
	@FXML
	TableColumn<RegRadio, Integer> columnIDTab2;
	@FXML
	TableColumn<RegRadio, String> columnCalibrationInfoTab2;

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

	/****************************** TAB 3 ****************************************/
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatumTab3;
	@FXML
	TableColumn<RegRadio, Double> columnActivityTab3;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplierTab3;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceuticalTab3;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdateTab3;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumberTab3;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControlTab3;
	@FXML
	TableColumn<RegRadio, Room> columnRoomTab3;
	@FXML
	TableColumn<RegRadio, User> columnUserTab3;
	@FXML
	TableColumn<RegRadio, Integer> columnIDTab3;
	@FXML

	TableColumn<RegRadio, String> columnCalibrationInfoTab3;

	private int aktivt;

	private int inaktivt;


	public void addProductsTabThree() {
		radioListTabThree.clear();
		radioListTabThree.addAll(new RadiopharmaceuticalDao().getAll());
		combobox_radio_tab_three.getItems().addAll(radioListTabTwo);
	}

	public void addRoomsTabThree() {
		combobox_room_tab_three.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getTrash()));
	}

	public void addUsersTabThree() {
		combobox_user_tab_three.getItems().addAll(FXCollections.observableArrayList(new UserDao().getAll()));
	}

	/**********************************************************************/	
	TableColumn<RegRadio, User> columnUser;





	private int i;




	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}
	public void addSubstances() {
		substanceList.addAll(new SubstanceDao().getAll());
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

	public void clearButtonTab2(ActionEvent search) throws Exception {
		this.event = search;
		combobox_room_tab_two.getSelectionModel().clearSelection();
		combobox_radio_tab_two.getSelectionModel().clearSelection();
		combobox_user_tab_two.getSelectionModel().clearSelection();
		endSortDateTab2.setValue(LocalDate.now());
		startSortDateTab2.setValue(getFirstDateFromDatabase().toLocalDate());
		editButtonTab2.setDisable(true);
		searchRegRadioListTab2.clear();
		populateListFromDatabase();
		searchRadioViewTab2.setItems(searchRegRadioListTab2);
		searchRadioViewTab2.refresh();
	}

	public void clearButtonTab3(ActionEvent search) throws Exception {
		this.event = search;
		combobox_room_tab_three.getSelectionModel().clearSelection();
		combobox_radio_tab_three.getSelectionModel().clearSelection();
		combobox_user_tab_three.getSelectionModel().clearSelection();
		endSortDateTab3.setValue(LocalDate.now());
		startSortDateTab3.setValue(getFirstDateFromDatabase().toLocalDate());
		searchRegRadioListTab3.clear();
		populateTab3ListFromDatabase();
		searchRadioViewTab3.setItems(searchRegRadioListTab3);
		searchRadioViewTab3.refresh();
	}

	public LocalDateTime getFirstDateFromDatabase() {
		LocalDateTime date = new RegRadioDao().getFirstDate();
		return date;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addSuppliers();
		addRooms();
		addUser();

		addRoomsTabTwo();
		addUsersTabTwo();
		addProductsTabTwo();

		addRoomsTabThree();
		addUsersTabThree();
		addProductsTabThree();

		signatur.setText(user.getSignature());
		ankomstdatum.setValue(LocalDate.now());
		endSortDateTab2.setValue(LocalDate.now());
		startSortDateTab2.setValue(getFirstDateFromDatabase().toLocalDate());
		endSortDateTab3.setValue(LocalDate.now());
		startSortDateTab3.setValue(getFirstDateFromDatabase().toLocalDate());
		combobox_radio.setDisable(true);
		discardButton.setDisable(true);
		aboutButton = new Button("",new ImageView(aboutIcon));
		feedBackTextAreaTabFour.setVisible(true);


		setUpTableView();
		setUpTableViewTabTwo();
		setUpTableViewTabThree();
		//new Thread(() -> populateListFromDatabase()).start();
		populateListFromDatabase();
		populateTab3ListFromDatabase();
		resetTabFour();

		formList.add("Övrigt");
		formList.add("Kapsel");
		formList.add("Lösning");

		roomActivityList.add("Ja");
		roomActivityList.add("Nej");


		////////////////////////////////////////////////



		///////////////////////////////////////////////////


		/****************************** TAB 1 ****************************************/
		combobox_suppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			combobox_radio.getItems().clear();
			combobox_radio.getItems().addAll(FXCollections.observableArrayList(
					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			combobox_radio.setDisable(false);
			combobox_radio.getSelectionModel().selectFirst();
		});

		combobox_radio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
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
		/****************************** TAB 2 ****************************************/
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
		editButtonTab2.setDisable(true);
		/****************************** TAB 3 ****************************************/
		combobox_radio_tab_three.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				radio_tab_three = newValue;
			} else {
				radio_tab_three = null;
			}
		});
		combobox_room_tab_three.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				room_tab_three = newValue;
			} else {
				room_tab_three = null;
			}
		});
		combobox_user_tab_three.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				user_tab_three = newValue;
			} else {
				user_tab_three = null;
			}
		});

		saveButton.setVisible(true);


		listOfTypesTabFour.add("Användare");
		listOfTypesTabFour.add("Leverantör");
		listOfTypesTabFour.add("Ämne");
		listOfTypesTabFour.add("RadioFarmaka");
		listOfTypesTabFour.add("Rum");

		typeComboBoxTabFour.getItems().addAll(listOfTypesTabFour);


		typeComboBoxTabFour.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			System.out.println("clicked"+ typeComboBoxTabFour.getSelectionModel().getSelectedIndex());
			//			combobox_radio.getItems().clear();
			//			combobox_radio.getItems().addAll(FXCollections.observableArrayList(
			//					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			//			combobox_radio.setDisable(false);
			//			combobox_radio.getSelectionModel().selectFirst();


			switch(typeComboBoxTabFour.getSelectionModel().getSelectedIndex()) {
			case 0:
				viewUserSettings();
				break;
			case 1:
				viewSupplierSettings();
				break;
			case 2:
				viewSubstanceSettings();
				break;
			case 3:
				viewProductSettings();
				break;
			case 4:
				viewRoomSettings();
				break;
			default:
				System.out.println("nothing chosen");
				break;

			}
		});	




	}



	private void saveProductButton() {

		new Thread() {
			@Override
			public void run() {
				RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(),
						text_batchnr.getText(), getContaminationControl(), combobox_radio.getValue(),
						combobox_room.getValue(), user, combobox_suppliers.getValue());
				new RegRadioDao().save(rr);
				System.out.println(getArrivalDate());
				regRadioList.clear();
				regRadioList.addAll(new RegRadioDao().getAll());
				regRadioList = FXCollections.observableArrayList(regRadioList.subList(0, tabOneNumberOfRows)); 
				tableview.getItems().clear();
				tableview.getItems().addAll(regRadioList);
				updateTables();
			}
		}.start();
	}

	public void populateListFromDatabase() {
		searchRegRadioListTab2.clear();
		searchRegRadioListTab2.addAll(new RegRadioDao().getAll());
		populateTabOneTablelist();
	}

	public void populateTab3ListFromDatabase() {
		searchRegRadioListTab3.clear();
		searchRegRadioListTab3.addAll(new RegRadioDao().getTrash());
	}

	public void populateTabOneTablelist() {
		regRadioList.clear();
		if(searchRegRadioListTab2.size() > tabOneNumberOfRows)
			regRadioList = FXCollections.observableArrayList(searchRegRadioListTab2.subList(0, tabOneNumberOfRows)); 
		else {
			regRadioList = searchRegRadioListTab2;
		}
		tableview.getItems().clear();
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

	public void setUpTableViewTabTwo() {
		columnIDTab2.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnSupplierTab2.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceuticalTab2.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnBatchNumberTab2.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnActivityTab2.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdateTab2.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnAnkomstdatumTab2.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnContaminationControlTab2.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoomTab2.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnCalibrationInfoTab2.setCellValueFactory(new PropertyValueFactory<>("calibrationInfo"));
		columnUserTab2.setCellValueFactory(new PropertyValueFactory<>("user"));

		searchRegRadioListTab2.clear();
		searchRadioViewTab2.setItems(searchRegRadioListTab2);
		addColumnNamesToListTab2();
	}

	public void setUpTableViewTabThree() {
		columnIDTab3.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnSupplierTab3.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceuticalTab3.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnBatchNumberTab3.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnActivityTab3.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdateTab3.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnAnkomstdatumTab3.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnContaminationControlTab3.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoomTab3.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnCalibrationInfoTab3.setCellValueFactory(new PropertyValueFactory<>("calibrationInfo"));
		columnUserTab3.setCellValueFactory(new PropertyValueFactory<>("user"));

		searchRegRadioListTab3.clear();
		searchRadioViewTab3.setItems(searchRegRadioListTab3);
		addColumnNamesToListTab3();
	}


	public void setUpTableView() {
		roomList = FXCollections.observableArrayList(new RoomDao().getAll());

		columnIdTab1.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnAnkomstdatumTab1.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnSupplierTab1.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceuticalTab1.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnActivityTab1.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdateTab1.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnBatchNumberTab1.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnContaminationControlTab1.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoomTab1.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnUserTab1.setCellValueFactory(new PropertyValueFactory<>("user"));

		tableview.getColumns().add(editColumn);


		tableview.setEditable(true);



		columnSupplierTab1.setCellFactory(ComboBoxTableCell.forTableColumn(supplierList));
		columnSupplierTab1.setOnEditCommit(t -> {
			ArrayList<Radiopharmaceutical> radioListfromSupplier = new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(t.getNewValue().getSupplierName());
			radioList.clear();
			radioList = FXCollections.observableArrayList(radioListfromSupplier);
			t.getRowValue().setSupplier(t.getNewValue());
			columnRadiopharmaceuticalTab1.setCellFactory(ComboBoxTableCell.forTableColumn(radioList));

			tableview.getSelectionModel().select(currentRowIndex);
			tableview.requestFocus();

		});

		columnRadiopharmaceuticalTab1.setCellFactory(ComboBoxTableCell.forTableColumn(radioList));
		columnRadiopharmaceuticalTab1.setOnEditCommit(t ->{

			tableview.getSelectionModel().select(currentRowIndex);
			tableview.requestFocus();

			t.getRowValue().setRadiopharmaceutical(t.getNewValue());

			//			columnSupplier.getTableView().requestFocus();
			//			addTableCellButton();
			tableview.requestFocus();
		});


		columnRoomTab1.setCellFactory(ComboBoxTableCell.forTableColumn(roomList));
		columnRoomTab1.setOnEditCommit(t ->{
			tableview.getSelectionModel().select(currentRowIndex);
			tableview.requestFocus();
			t.getRowValue().setRoom(t.getNewValue());
		});

		columnBatchNumberTab1.setCellFactory(TextFieldTableCell.forTableColumn());
		columnBatchNumberTab1.setOnEditCommit(t -> {
			t.getRowValue().setBatchNumber(t.getNewValue());  
			tableview.getSelectionModel().select(currentRowIndex);
			tableview.requestFocus();

		});

		columnContaminationControlTab1.setCellFactory(TextFieldTableCell.forTableColumn());
		columnContaminationControlTab1.setOnEditCommit(t -> {
			t.getRowValue().setContaminationControll(t.getNewValue());  
			tableview.getSelectionModel().select(currentRowIndex);
			tableview.requestFocus();

		});



		tableview.setOnMouseClicked(t ->{

			currentRowIndex = tableview.getSelectionModel().getSelectedIndex();
			if(currentRowIndex != -1) {
				RegRadio lastRegradio = tableview.getItems().get(currentRowIndex);
				System.out.println(tableview.getItems().get(currentRowIndex).toString());
			}
		});

		columnRadiopharmaceuticalTab1.setCellFactory(ComboBoxTableCell.forTableColumn(radioList));
		tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null) {
				oldRegRadio = newValue;
				System.out.println("Change Row");
				if(oldValue != null)
					System.out.println("Old Value: " + oldValue);
				System.out.println("New Value: " + newValue);
				ArrayList<Radiopharmaceutical> radioListfromSupplier = new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.getSupplier().getSupplierName());
				radioList.clear();
				radioList = FXCollections.observableArrayList(radioListfromSupplier);
				columnRadiopharmaceuticalTab1.setCellFactory(ComboBoxTableCell.forTableColumn(radioList));
				addTableCellButton();

			}
		});


		tableview.focusedProperty().addListener((obs, oldValue, newValue) ->{
			if(newValue) {
				System.out.println("focus");
				System.out.println(tableview.getFocusModel().getFocusedItem().getRadiopharmaceutical().getSupplier());


				columnSupplierTab1.setCellFactory(ComboBoxTableCell.forTableColumn(supplierList));	

			}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				

			else {
				if(obs.equals(tableview))
					//		System.out.println("lost focus");
					tableview.getSelectionModel().clearSelection();
			}
		});

	}





	private void addTableCellButton() {
	    int selectedRowIndex = tableview.getSelectionModel().getSelectedIndex();
	    System.out.println("selectedrowindex: " + selectedRowIndex + " Something fishy here!!");
	   
	    
//	    Callback<TableColumn<RegRadio, Date>, TableCell<RegRadio, Date>> cellDateFactory = new Callback<TableColumn<RegRadio, Date>,TableCell<RegRadio, Date>>() {
//
//			@Override
//			public TableCell<RegRadio, Date> call(TableColumn<RegRadio, Date> param) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
	    
	    
	    
	    
	    
	    
	    Callback<TableColumn<RegRadio,String>,TableCell<RegRadio,String>> cellFactory = 
	            new Callback<TableColumn<RegRadio,String>,TableCell<RegRadio,String>>() {
	                @Override
	                public TableCell<RegRadio, String> call(TableColumn<RegRadio, String> param) {
	                	TableCell<RegRadio, String> cell = new TableCell<RegRadio, String>() {
	                		
	                        	Image saveIcon = new Image(getClass().getResourceAsStream("/icons/Save-icon.png"),16,16,true,true);
	                        	Image cancelIcon = new Image(getClass().getResourceAsStream("/icons/icons8-delete.png"),16,16,true,true);
	                        	Image deleteIcon = new Image(getClass().getResourceAsStream("/icons/icons8-trash.png"),16,16,true,true);
	                			
	                        	
	                        	final Button btnSave = new Button("",new ImageView(saveIcon));
	                			final Button btnAbort = new Button("",new ImageView(cancelIcon));
	                			final Button btnDelete = new Button("",new ImageView(deleteIcon));
	                			HBox buttons =new HBox(btnSave, btnAbort, btnDelete);
	                        
	                         
	                        @Override
	                        public void updateItem(String item, boolean empty) {
	                        	setOnMouseClicked(t -> {
	                        	
	                        	});
//	                        	
//	                        	
//	                        	
	                        	  System.out.println("getIndex: " + getIndex());
	                        	super.updateItem(item, empty);
	                            if (empty || getIndex() == -1 ||  getIndex() != selectedRowIndex  ) {
	                            	//|| !tableview.isFocused()
	                            	setGraphic(null);
	                                //setText(null);                                  
	                            } else {
	                                // Do update here
	                                btnSave.setOnAction(event -> {
	                                	System.out.println(item);
	                                	
	                                	getTableView().refresh();
	                                	RegRadio rr = getTableView().getItems().get(getIndex());
	                                	System.out.println(getTableRow().getItem().toString());
	                                 
	                                    new RegRadioDao().updateAndReplace(rr, rr);
		                                searchRadioViewTab2.refresh();
	                                    
	                                    setGraphic(null);
		                                setText(null);  
		                                
	                                });
	                                
	                                btnAbort.setOnAction(event -> {
//	                                	tableview.getColumns().clear();
//	                                	tableview.getItems().addAll(regRadioList);
//	                                	
//	                                	getTableView().refresh();
	                                	System.out.println("Abort");

	                                    setGraphic(null);
		                                setText(null);  
		                              
		                                
	                                });
	                                btnDelete.setOnAction(event -> {
	                                System.out.println("Delete");
	                                
	                                RegRadio selectedRow = tableview.getItems().get(currentRowIndex);
	                                System.out.println(selectedRow);
	                                tableview.getItems().remove(selectedRow);
	                                new RegRadioDao().delete(selectedRow);
	                                searchRegRadioList.remove(selectedRow);
	                                searchRadioViewTab2.refresh();
	                                
	                                setGraphic(null);
		                                setText(null);  
		                                
	                                });
	                                setGraphic(buttons);
	                                setText(null);
	                            }
	                        }
	                    };
	 
	                    return cell;
	                }
	         
	    };
	     
	    editColumn.setCellFactory(cellFactory);
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


	public void clickedSearchScrollPaneTab2() {
		editButtonTab2.setDisable(false);
		discardButton.setDisable(false);
		System.out.println("clicked scrollpane");
		chosenRegRadioTab2 = (RegRadio) searchRadioViewTab2.getSelectionModel().getSelectedItem();
		chosenRegRadioTab2.print();
	}

	public void clickedSearchScrollPaneTab3() {
		editButtonTab3.setDisable(false);
		System.out.println("clicked scrollpane");
		chosenRegRadioTab3 = (RegRadio) searchRadioViewTab3.getSelectionModel().getSelectedItem();
		chosenRegRadioTab3.print();
	}

	public void clickedActivityButton(ActionEvent logout) throws Exception {
		System.out.println("clicked activity");
		DataHolder.setSavedRadio(chosenRegRadioTab2);
		Parent root = FXMLLoader.load(getClass().getResource("ActivityControl.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Aktivitetskontroll");
		stage.setScene(new Scene(root));
		stage.showAndWait();
		updateTables();
	}

	private void updateTables() {
		aktivt = 1;
		inaktivt = 0;
		searchRegRadioListTab2.clear();
		searchRegRadioListTab2.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDateTab2(), getEndSortDateTab2(), radio_tab_two, room_tab_two, user_tab_two, aktivt));
		searchRadioViewTab2.setItems(searchRegRadioListTab2);
		searchRadioViewTab2.refresh();	
		searchRegRadioListTab3.clear();
		searchRegRadioListTab3.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDateTab3(), getEndSortDateTab3(), radio_tab_three, room_tab_three, user_tab_three, inaktivt));
		searchRadioViewTab3.setItems(searchRegRadioListTab3);
		searchRadioViewTab3.refresh();	
	}

	public Date getStartSortDateTab2() {
		start = java.sql.Date.valueOf(startSortDateTab2.getValue());
		return start;
	}

	public Date getEndSortDateTab2() {
		end = java.sql.Date.valueOf(endSortDateTab2.getValue());
		return end;
	}

	public Date getStartSortDateTab3() {
		start = java.sql.Date.valueOf(startSortDateTab3.getValue());
		return start;
	}

	public Date getEndSortDateTab3() {
		end = java.sql.Date.valueOf(endSortDateTab3.getValue());
		return end;
	}	

	public void searchButtonTab2Action(ActionEvent search) throws Exception {
		this.event = search;
		aktivt = 1;
		searchRegRadioListTab2.clear();
		searchRegRadioListTab2.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDateTab2(), getEndSortDateTab2(), radio_tab_two, room_tab_two, user_tab_two, aktivt));
		searchRadioViewTab2.setItems(searchRegRadioListTab2);
	}

	public void searchButtonTab3Action(ActionEvent search) throws Exception {
		this.event = search;
		aktivt = 0;
		searchRegRadioListTab3.clear();
		searchRegRadioListTab3.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDateTab3(), getEndSortDateTab3(), radio_tab_three, room_tab_three, user_tab_three, aktivt));
		searchRadioViewTab3.setItems(searchRegRadioListTab3);
	}

	public void writeTableViewToExcelTab2() {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			String ebola = file.toPath().toString();
			System.out.println(ebola);
			WriteToExcelController writeExCon = new WriteToExcelController();
			writeExCon.execute(searchRadioViewTab2, columnHeaderList,file);
		}
	}

	private void addColumnNamesToListTab2() {

		columnHeaderList.add(columnIDTab2.getText());
		columnHeaderList.add(columnSupplierTab2.getText());
		columnHeaderList.add(columnRadiopharmaceuticalTab2.getText());
		columnHeaderList.add(columnBatchNumberTab2.getText());
		columnHeaderList.add(columnActivityTab2.getText());
		columnHeaderList.add(columnCalibrationdateTab2.getText());
		columnHeaderList.add(columnAnkomstdatumTab2.getText());
		columnHeaderList.add(columnRoomTab2.getText());
		columnHeaderList.add(columnCalibrationInfoTab2.getText());
		columnHeaderList.add(columnUserTab2.getText());
	}

	public void writeTableViewToExcelTab3() {

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			String path = file.toPath().toString();
			System.out.println(path);
			WriteToExcelController writeExCon = new WriteToExcelController();
			writeExCon.execute(searchRadioViewTab3, columnHeaderList,file);
		}
	}

	private void addColumnNamesToListTab3() {

		columnHeaderList.add(columnIDTab3.getText());
		columnHeaderList.add(columnSupplierTab3.getText());
		columnHeaderList.add(columnRadiopharmaceuticalTab3.getText());
		columnHeaderList.add(columnBatchNumberTab3.getText());
		columnHeaderList.add(columnActivityTab3.getText());
		columnHeaderList.add(columnCalibrationdateTab3.getText());
		columnHeaderList.add(columnAnkomstdatumTab3.getText());
		columnHeaderList.add(columnRoomTab3.getText());
		columnHeaderList.add(columnCalibrationInfoTab3.getText());
		columnHeaderList.add(columnUserTab3.getText());

	}

	public void clickedDiscardButton() throws IOException {
		DataHolder.setSavedRadio(chosenRegRadioTab2);
		System.out.println("clicked discard button");
		Parent root = FXMLLoader.load(getClass().getResource("ChooseDiscardGui.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Kasseringsmetod");
		stage.setScene(new Scene(root));
		stage.showAndWait();
		updateTables();
		//updateTableTab3();
	}

	public void clickedAboutButton() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AboutWindow.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Information");
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void viewUserSettings() {

		resetTabFour();
		System.out.println("0");
		firstTextFieldAttributeLabelTabFour.setText("Användarens Signatur");
		firstTextFieldAttributeLabelTabFour.setVisible(true);
		firstTextFieldTabFour.setVisible(true);
		firstTextFieldAttributeLabelTabFour.setVisible(true);

	}
	public void viewSupplierSettings() {
		resetTabFour();
		System.out.println("1");
		firstTextFieldAttributeLabelTabFour.setText("Leverantörens namn");
		firstTextFieldAttributeLabelTabFour.setVisible(true);
		firstTextFieldTabFour.setVisible(true);
		firstTextFieldAttributeLabelTabFour.setVisible(true);


	}
	public void viewSubstanceSettings() {
		resetTabFour();
		System.out.println("2");

		firstTextFieldAttributeLabelTabFour.setText("Ämnets namn");
		firstTextFieldAttributeLabelTabFour.setVisible(true);
		firstTextFieldTabFour.setVisible(true);

		secondTextFieldAttributeLabelTabFour.setText("Halveringstid");
		secondTextFieldAttributeLabelTabFour.setVisible(true);
		secondTextFieldTabFour.setVisible(true);

	}
	public void viewProductSettings() {
		resetTabFour();
		System.out.println("3");
		System.out.println("storlek "+supplierList.size());
		addSubstances();
		thirdComboBox.getItems().addAll(formList);

		selectSupplierComboBoxTabFour.getItems().addAll(supplierList);
		selectSubstanceComboBoxTabFour.getItems().addAll(substanceList);

		firstComboBoxAttributeLabelTabFour.setText("Leverantör");
		secondComboBoxAttributeLabelTabFour.setText("Ämne");
		firstTextFieldAttributeLabelTabFour.setText("Produktens namn");
		thirdComboBoxAttributeLabelTabFour.setText("Form");

		selectSupplierComboBoxTabFour.setVisible(true);
		firstComboBoxAttributeLabelTabFour.setVisible(true);

		selectSubstanceComboBoxTabFour.setVisible(true);
		secondComboBoxAttributeLabelTabFour.setVisible(true);

		thirdComboBox.setVisible(true);
		thirdComboBoxAttributeLabelTabFour.setVisible(true);



		firstTextFieldAttributeLabelTabFour.setVisible(true);
		firstTextFieldTabFour.setVisible(true);

	}
	private void viewRoomSettings() {
		resetTabFour();
		System.out.println("4");
		thirdComboBox.getItems().addAll(roomActivityList);

		firstTextFieldAttributeLabelTabFour.setText("Rumsbeskrivning");
		firstTextFieldAttributeLabelTabFour.setVisible(true);
		firstTextFieldTabFour.setVisible(true);

		secondTextFieldAttributeLabelTabFour.setText("Rumkod");
		secondTextFieldAttributeLabelTabFour.setVisible(true);
		secondTextFieldTabFour.setVisible(true);

		thirdComboBoxAttributeLabelTabFour.setText("Är rummet ett kasseringsrum?");
		thirdComboBoxAttributeLabelTabFour.setVisible(true);
		thirdComboBox.setVisible(true);


	}
	private void resetTabFour() {
		
		selectSubstanceComboBoxTabFour.getItems().clear();
		selectSupplierComboBoxTabFour.getItems().clear();
		thirdComboBox.getItems().clear();
		System.out.println("reset");

		selectSupplierComboBoxTabFour.getSelectionModel().clearSelection();
		selectSubstanceComboBoxTabFour.getSelectionModel().clearSelection();
		firstTextFieldTabFour.setText(null);
		secondTextFieldTabFour.setText(null);
		thirdComboBoxAttributeLabelTabFour.setText(null);

		selectSupplierComboBoxTabFour.setVisible(false);
		selectSubstanceComboBoxTabFour.setVisible(false);
		firstTextFieldTabFour.setVisible(false);
		secondTextFieldTabFour.setVisible(false);

		firstComboBoxAttributeLabelTabFour.setVisible(false);
		secondComboBoxAttributeLabelTabFour.setVisible(false);
		firstTextFieldAttributeLabelTabFour.setVisible(false);
		secondTextFieldAttributeLabelTabFour.setVisible(false);
		//		feedBackTextAreaTabFour.setVisible(false);
		thirdComboBox.setVisible(false);
		thirdComboBoxAttributeLabelTabFour.setVisible(false);

	}
	public void clickedHelpButton() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HelpGUI.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Hjälp");
		stage.setScene(new Scene(root));
		stage.showAndWait();

	}

	public void clickedSaveButtonTabFour() {

		try {

			switch(typeComboBoxTabFour.getSelectionModel().getSelectedIndex()) {
			case 0:

				User newUser = new User(firstTextFieldTabFour.getText());
				UserDao uDao = new UserDao();
				if(uDao.save(newUser))
				{
					feedBackTextAreaTabFour.setText("Sparade ny profil \n signatur: "+newUser.getSignature()
					+"\n lösenord: "+newUser.getPassword());
					System.out.println("a");

				}
				else
				{
					feedBackTextAreaTabFour.setText("Misslyckades med att spara profil");
				}


				break;

			case 1:

				Supplier newSupplier = new Supplier(firstTextFieldTabFour.getText());
				SupplierDao sDao = new SupplierDao();

				if(sDao.save(newSupplier))
				{
					feedBackTextAreaTabFour.setText("Sparade ny leverantör \n namn: "+newSupplier.getSupplierName());

				}
				else
				{
					feedBackTextAreaTabFour.setText("Misslyckades med att spara leverantör");
				}

				break;
			case 2:

				Substance newSubstance = new Substance(firstTextFieldTabFour.getText(),Double.parseDouble(secondTextFieldTabFour.getText()));
				SubstanceDao suDao = new SubstanceDao();
				if(suDao.save(newSubstance))
				{
					feedBackTextAreaTabFour.setText("Sparade nytt ämne: \n namn: "+
							newSubstance.getName()+"\n halveringstid: "+newSubstance.getHalfLife());
				}
				else
				{
					feedBackTextAreaTabFour.setText("Misslyckades med att spara ämne");
				}

				break;
			case 3:

				Radiopharmaceutical newRadioPharma = new Radiopharmaceutical(firstTextFieldTabFour.getText(),
						thirdComboBox.getSelectionModel().getSelectedItem(),
						selectSubstanceComboBoxTabFour.getSelectionModel().getSelectedItem(),
						selectSupplierComboBoxTabFour.getSelectionModel().getSelectedItem());
				RadiopharmaceuticalDao rDao = new RadiopharmaceuticalDao();

				if(rDao.save(newRadioPharma))
				{
					feedBackTextAreaTabFour.setText("Sparade nytt radiofarmaka. \n Namn: "+newRadioPharma.getRadiopharmaceuticalName()+"\n form: "+newRadioPharma.getForm()
					+"\n leverantör: "+newRadioPharma.getSupplier()+"\n ämne: "+newRadioPharma.getSubstance());
				}
				else
				{
					feedBackTextAreaTabFour.setText("Misslyckades med att spara radiofarmaka");
				}

				break;

			case 4:

				Boolean isActive;
				if(thirdComboBox.getSelectionModel().getSelectedItem().equals("Ja"))
				{
					isActive=false;
				}
				else
				{
					isActive=true;
				}
				Room newRoom = new Room(firstTextFieldTabFour.getText(),secondTextFieldTabFour.getText(),isActive);
				RoomDao roomDao = new RoomDao();

				if(roomDao.save(newRoom))
				{
					feedBackTextAreaTabFour.setText("Sparade nytt rum. \n rumskod: "
							+newRoom.getRoomCode()+"\n beksrivning: "+newRoom.getDescription());
				}
				else
				{
					feedBackTextAreaTabFour.setText("Misslyckades med att spara nytt rum");
				}


				break;
			default:
				feedBackTextAreaTabFour.setText("Något blev fel, inget har sparats till databasen");
				break;

			}
		}catch(Exception e) {
			feedBackTextAreaTabFour.setText("Något blev fel, inget har sparats till databasen");
			System.out.println("error");
		}
	}


}

